package dev.abhishekagrahari.questionbank.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val ProfessionalDarkColorScheme = darkColorScheme(
    primary = Color(0xFF0A3D62), // Dark Blue
    secondary = Color(0xFFA4B0BE), // Soft Gray
    tertiary = Color(0xFF16A085), // Teal
    background = Color(0xFF1B1B1B),
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val ProfessionalLightColorScheme = lightColorScheme(
    primary = Color(0xFF0A3D62), // Dark Blue
    secondary = Color(0xFFA4B0BE), // Soft Gray
    tertiary = Color(0xFF16A085), // Teal
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val CasualDarkColorScheme = darkColorScheme(
    primary = Color(0xFF9C27B0), // Bright Purple
    secondary = Color(0xFFFF9800), // Vibrant Orange
    tertiary = Color(0xFF4CAF50), // Soft Green
    background = Color(0xFF212121),
    surface = Color(0xFF303030),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val CasualLightColorScheme = lightColorScheme(
    primary = Color(0xFF9C27B0), // Bright Purple
    secondary = Color(0xFFFF9800), // Vibrant Orange
    tertiary = Color(0xFF4CAF50), // Soft Green
    background = Color.White,
    surface = Color(0xFFF8F8F8),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun QuestionBankTheme(
    themeType: String = "professional", // Choose "casual" or "professional"
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeType) {
        "casual" -> if (darkTheme) CasualDarkColorScheme else CasualLightColorScheme
        else -> if (darkTheme) ProfessionalDarkColorScheme else ProfessionalLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
