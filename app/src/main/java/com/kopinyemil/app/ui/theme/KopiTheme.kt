package com.kopinyemil.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ---------- Presisi Palet Kopi & Camilan Sesuai Desain ----------
val DarkBrownHeader = Color(0xFF2B140E)
val DarkBrownHeaderEnd = Color(0xFF452218)
val CaramelGold = Color(0xFFC3864A)
val CaramelLight = Color(0xFFDDA66B)
val WarmBeige = Color(0xFFF4ECE4)
val CardCream = Color(0xFFFAF7F2)
val DarkButton = Color(0xFF3B1E17)
val PillBorder = Color(0xFFDDD2C6)
val PillTextUnselected = Color(0xFF7D6C5F)

val Espresso = Color(0xFF2C140E)
val Coffee = Color(0xFF512A1E)
val Caramel = Color(0xFFC3864A)
val Latte = Color(0xFFEADBCE)
val Cream = Color(0xFFF4ECE4)
val Matcha = Color(0xFF8AA67B)
val TextDark = Color(0xFF23130E)
val TextMuted = Color(0xFF807063)

// ---------- Tipografi Elegan ----------
val HeadingFont = FontFamily.Serif

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp)
)

// ---------- Color Scheme Material 3 ----------
private val CafeColorScheme = lightColorScheme(
    primary = DarkBrownHeader,
    onPrimary = WarmBeige,
    secondary = CaramelGold,
    onSecondary = Color.White,
    background = WarmBeige,
    onBackground = TextDark,
    surface = Color.White,
    onSurface = TextDark,
    surfaceVariant = Latte,
    outline = PillBorder,
    error = Color(0xFFB3261E)
)

@Composable
fun KopiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CafeColorScheme,
        typography = AppTypography,
        content = content
    )
}
