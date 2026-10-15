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
import sk.futbalkviz.data.Question
import sk.futbalkviz.data.Questions

const val QUESTIONS_PER_ROUND = 10
const val SECONDS_PER_QUESTION = 15

data class RoundQuestion(
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
)

sealed interface QuizState {
    data class Home(val bestScore: Int) : QuizState

    data class Playing(
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

class QuizViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = app.getSharedPreferences("futbal_kviz", Context.MODE_PRIVATE)

    private val _state = MutableStateFlow<QuizState>(QuizState.Home(bestScore()))
    val state: StateFlow<QuizState> = _state.asStateFlow()

    private var timerJob: Job? = null

    private fun bestScore(): Int = prefs.getInt(KEY_BEST, 0)

    fun start() {
        _state.value = QuizState.Playing(
            questions = buildRound(Questions.all),
            index = 0,
            secondsLeft = SECONDS_PER_QUESTION,
            selected = null,
            timedOut = false,
            score = 0,
            correctCount = 0,
        )
        startTimer()
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
        _state.value = QuizState.Home(bestScore())
    }

    private fun finish(s: QuizState.Playing) {
        val previousBest = bestScore()
        val newRecord = s.score > previousBest
        if (newRecord) prefs.edit().putInt(KEY_BEST, s.score).apply()
        _state.value = QuizState.Finished(
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
        const val KEY_BEST = "best_score"
    }
}
