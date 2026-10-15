package sk.futbalkviz.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.futbalkviz.QUESTIONS_PER_ROUND
import sk.futbalkviz.QuizState
import sk.futbalkviz.SECONDS_PER_QUESTION

private val ButtonShape = RoundedCornerShape(14.dp)

@Composable
fun HomeScreen(state: QuizState.Home, onStart: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("⚽", fontSize = 72.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            "Futbalový kvíz",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            "Európske kluby",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(32.dp))
        Text(
            "$QUESTIONS_PER_ROUND otázok. $SECONDS_PER_QUESTION sekúnd na každú.\n" +
                "Čím rýchlejšie odpovieš, tým viac bodov.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.height(24.dp))
        Text(
            "Najlepšie skóre: ${state.bestScore}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onStart,
            shape = ButtonShape,
            modifier = Modifier.fillMaxWidth().height(56.dp),
        ) {
            Text("Hrať", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun QuestionScreen(
    state: QuizState.Playing,
    onAnswer: (Int) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val q = state.current
    val timeFraction by animateFloatAsState(
        targetValue = state.secondsLeft / SECONDS_PER_QUESTION.toFloat(),
        animationSpec = tween(durationMillis = 900),
        label = "timer",
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Otázka ${state.index + 1} / ${state.questions.size}", fontWeight = FontWeight.SemiBold)
            Text("Body: ${state.score}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = { timeFraction },
                modifier = Modifier.weight(1f).height(8.dp),
                color = if (state.secondsLeft <= 5) WrongRed else MaterialTheme.colorScheme.primary,
                trackColor = PitchDark,
            )
            Spacer(Modifier.padding(start = 12.dp))
            Text("${state.secondsLeft} s", fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(24.dp))
        Text(
            q.text,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(24.dp))

        q.options.forEachIndexed { i, option ->
            AnswerButton(
                text = option,
                status = answerStatus(i, state),
                enabled = !state.answered,
                onClick = { onAnswer(i) },
            )
            Spacer(Modifier.height(10.dp))
        }

        AnimatedVisibility(visible = state.answered) {
            Column {
                Spacer(Modifier.height(8.dp))
                val correct = state.selected == q.correctIndex
                val headline = when {
                    state.timedOut -> "Čas vypršal"
                    correct -> "Správne!"
                    else -> "Nesprávne"
                }
                Card(
                    colors = CardDefaults.cardColors(containerColor = PitchDark),
                    shape = ButtonShape,
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            headline,
                            fontWeight = FontWeight.Bold,
                            color = if (correct) CorrectGreen else WrongRed,
                            fontSize = 18.sp,
                        )
                        if (!correct) {
                            Text("Správna odpoveď: ${q.options[q.correctIndex]}", fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(q.explanation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onNext,
                    shape = ButtonShape,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                ) {
                    Text(if (state.isLast) "Výsledky" else "Ďalšia otázka", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

private enum class AnswerStatus { Neutral, Correct, Wrong, Dimmed }

private fun answerStatus(i: Int, s: QuizState.Playing): AnswerStatus = when {
    !s.answered -> AnswerStatus.Neutral
    i == s.current.correctIndex -> AnswerStatus.Correct
    i == s.selected -> AnswerStatus.Wrong
    else -> AnswerStatus.Dimmed
}

@Composable
private fun AnswerButton(text: String, status: AnswerStatus, enabled: Boolean, onClick: () -> Unit) {
    val container = when (status) {
        AnswerStatus.Neutral -> MaterialTheme.colorScheme.surfaceVariant
        AnswerStatus.Correct -> CorrectGreen
        AnswerStatus.Wrong -> WrongRed
        AnswerStatus.Dimmed -> PitchDark
    }
    val content = if (status == AnswerStatus.Dimmed) Chalk.copy(alpha = 0.55f) else Chalk
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content,
            disabledContainerColor = container,
            disabledContentColor = content,
        ),
        modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
    ) {
        Text(text, fontSize = 16.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun ResultScreen(
    state: QuizState.Finished,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(if (state.newRecord) "🏆" else "⚽", fontSize = 72.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            if (state.newRecord) "Nový rekord!" else "Koniec hry",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = if (state.newRecord) MaterialTheme.colorScheme.primary else Color.Unspecified,
        )
        Spacer(Modifier.height(24.dp))
        Text("${state.score} bodov", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Text(
            "Správne odpovede: ${state.correctCount} z ${state.total}",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(8.dp))
        Text(verdict(state.correctCount, state.total), textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Text("Najlepšie skóre: ${state.bestScore}", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onPlayAgain,
            shape = ButtonShape,
            modifier = Modifier.fillMaxWidth().height(56.dp),
        ) {
            Text("Hrať znova", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = onHome,
            shape = ButtonShape,
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Text("Domov", color = Chalk)
        }
    }
}

private fun verdict(correct: Int, total: Int): String {
    val ratio = correct.toFloat() / total
    return when {
        ratio >= 0.9f -> "Futbalový expert. Klobúk dole."
        ratio >= 0.7f -> "Výborne. Európsky futbal poznáš."
        ratio >= 0.4f -> "Slušný výkon. Skús to znova."
        else -> "Ešte trochu tréningu na lavičke."
    }
}
