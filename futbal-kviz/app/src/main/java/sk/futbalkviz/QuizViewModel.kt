package sk.futbalkviz

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.futbalkviz.data.Club
import sk.futbalkviz.data.Clubs
import sk.futbalkviz.data.Question
import sk.futbalkviz.data.Questions

const val QUESTIONS_PER_ROUND = 10
const val SECONDS_PER_QUESTION = 15

enum class GameMode(val prefsKey: String) {
    General("best_score"),
    Logos("best_score_logos"),
}

data class RoundQuestion(
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    /** Kľúč loga v assets/logos, ak ide o otázku s logom. */
    val logoKey: String? = null,
)

sealed interface QuizState {
    data class Home(
        val bestScore: Int,
        val bestLogoScore: Int,
        val logosAvailable: Boolean,
    ) : QuizState

    data class Playing(
        val mode: GameMode,
        val questions: List<RoundQuestion>,
        val index: Int,
        val secondsLeft: Int,
        val selected: Int?,
        val timedOut: Boolean,
        val score: Int,
        val correctCount: Int,
    ) : QuizState {
        val current: RoundQuestion get() = questions[index]
        val answered: Boolean get() = selected != null || timedOut
        val isLast: Boolean get() = index == questions.lastIndex
    }

    data class Finished(
        val mode: GameMode,
        val score: Int,
        val correctCount: Int,
        val total: Int,
        val bestScore: Int,
        val newRecord: Boolean,
    ) : QuizState
}

/** Body za správnu odpoveď: 10 základ + 1 za každú zostávajúcu sekundu. */
fun pointsFor(secondsLeft: Int): Int = 10 + secondsLeft

fun buildRound(pool: List<Question>, count: Int = QUESTIONS_PER_ROUND): List<RoundQuestion> =
    pool.shuffled().take(count).map { q ->
        val options = (q.wrong + q.answer).shuffled()
        RoundQuestion(q.text, options, options.indexOf(q.answer), q.explanation)
    }

/** Kolo s logami: hádaný klub aj nesprávne možnosti sú len z klubov, ktorých logo je k dispozícii. */
fun buildLogoRound(clubs: List<Club>, count: Int = QUESTIONS_PER_ROUND): List<RoundQuestion> =
    clubs.shuffled().take(count).map { club ->
        val wrong = (clubs - club).shuffled().take(3).map { it.name }
        val options = (wrong + club.name).shuffled()
        RoundQuestion(
            text = "Ktorý klub má toto logo?",
            options = options,
            correctIndex = options.indexOf(club.name),
            explanation = club.fact,
            logoKey = club.key,
        )
    }

class QuizViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = app.getSharedPreferences("futbal_kviz", Context.MODE_PRIVATE)

    private val logoClubs: List<Club> = run {
        val files = app.assets.list(LOGO_DIR).orEmpty().toSet()
        Clubs.all.filter { "${it.key}.png" in files }
    }

    private val _state = MutableStateFlow<QuizState>(homeState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

    private var timerJob: Job? = null

    private fun best(mode: GameMode): Int = prefs.getInt(mode.prefsKey, 0)

    private fun homeState() = QuizState.Home(
        bestScore = best(GameMode.General),
        bestLogoScore = best(GameMode.Logos),
        logosAvailable = logoClubs.size >= MIN_LOGO_CLUBS,
    )

    fun start(mode: GameMode) {
        val questions = when (mode) {
            GameMode.General -> buildRound(Questions.all)
            GameMode.Logos -> {
                if (logoClubs.size < MIN_LOGO_CLUBS) return
                buildLogoRound(logoClubs)
            }
        }
        _state.value = QuizState.Playing(
            mode = mode,
            questions = questions,
            index = 0,
            secondsLeft = SECONDS_PER_QUESTION,
            selected = null,
            timedOut = false,
            score = 0,
            correctCount = 0,
        )
        startTimer()
    }

    fun playAgain() {
        val mode = (_state.value as? QuizState.Finished)?.mode ?: GameMode.General
        start(mode)
    }

    fun answer(optionIndex: Int) {
        val s = _state.value as? QuizState.Playing ?: return
        if (s.answered) return
        timerJob?.cancel()
        val correct = optionIndex == s.current.correctIndex
        _state.value = s.copy(
            selected = optionIndex,
            score = if (correct) s.score + pointsFor(s.secondsLeft) else s.score,
            correctCount = if (correct) s.correctCount + 1 else s.correctCount,
        )
    }

    fun next() {
        val s = _state.value as? QuizState.Playing ?: return
        if (!s.answered) return
        if (s.isLast) {
            finish(s)
        } else {
            _state.value = s.copy(
                index = s.index + 1,
                secondsLeft = SECONDS_PER_QUESTION,
                selected = null,
                timedOut = false,
            )
            startTimer()
        }
    }

    fun goHome() {
        timerJob?.cancel()
        _state.value = homeState()
    }

    private fun finish(s: QuizState.Playing) {
        val previousBest = best(s.mode)
        val newRecord = s.score > previousBest
        if (newRecord) prefs.edit().putInt(s.mode.prefsKey, s.score).apply()
        _state.value = QuizState.Finished(
            mode = s.mode,
            score = s.score,
            correctCount = s.correctCount,
            total = s.questions.size,
            bestScore = maxOf(previousBest, s.score),
            newRecord = newRecord,
        )
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                var stop = false
                _state.update { st ->
                    if (st !is QuizState.Playing || st.answered) {
                        stop = true
                        st
                    } else if (st.secondsLeft <= 1) {
                        stop = true
                        st.copy(secondsLeft = 0, timedOut = true)
                    } else {
                        st.copy(secondsLeft = st.secondsLeft - 1)
                    }
                }
                if (stop) break
            }
        }
    }

    private companion object {
        const val LOGO_DIR = "logos"
        const val MIN_LOGO_CLUBS = 4
    }
}
