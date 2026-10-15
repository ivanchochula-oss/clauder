package sk.futbalkviz.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Pitch = Color(0xFF0B5D3B)
val PitchDark = Color(0xFF07402A)
val Chalk = Color(0xFFF4F7F2)
val Gold = Color(0xFFF2C14E)
val CorrectGreen = Color(0xFF2E9E5B)
val WrongRed = Color(0xFFD64545)

private val colors = darkColorScheme(
    primary = Gold,
    onPrimary = Color(0xFF2A2000),
    secondary = Chalk,
    background = Pitch,
    onBackground = Chalk,
    surface = PitchDark,
    onSurface = Chalk,
    surfaceVariant = Color(0xFF14704A),
    onSurfaceVariant = Chalk,
)

@Composable
fun FutbalKvizTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colors, content = content)
}
