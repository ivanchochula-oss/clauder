package sk.futbalkviz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.futbalkviz.ui.FutbalKvizTheme
import sk.futbalkviz.ui.HomeScreen
import sk.futbalkviz.ui.QuestionScreen
import sk.futbalkviz.ui.ResultScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FutbalKvizTheme {
                val vm: QuizViewModel = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()

                BackHandler(enabled = state !is QuizState.Home) { vm.goHome() }

                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    val m = Modifier.fillMaxSize().padding(padding)
                    when (val s = state) {
                        is QuizState.Home -> HomeScreen(s, onStart = vm::start, modifier = m)
                        is QuizState.Playing -> QuestionScreen(s, onAnswer = vm::answer, onNext = vm::next, modifier = m)
                        is QuizState.Finished -> ResultScreen(s, onPlayAgain = vm::playAgain, onHome = vm::goHome, modifier = m)
                    }
                }
            }
        }
    }
}
