package com.tecsup.murocampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Colores = lightColorScheme(
    primary = Petroleo,
    onPrimary = Blanco,
    primaryContainer = CelesteSuave,
    onPrimaryContainer = Petroleo,
    secondary = Naranja,
    onSecondary = PetroleoOscuro,
    secondaryContainer = Naranja,
    onSecondaryContainer = PetroleoOscuro,
    background = CelesteFondo,
    onBackground = TextoPrincipal,
    surface = Blanco,
    onSurface = TextoPrincipal,
    surfaceVariant = CelesteSuave,
    onSurfaceVariant = TextoSecundario,
    outline = BordeSuave,
    error = RojoError,
    onError = Blanco,
    errorContainer = FondoError,
    onErrorContainer = RojoError
)

private val Tipografia = Typography(
    titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
    labelMedium = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
    labelSmall = TextStyle(fontSize = 12.sp)
)

@Composable
fun MuroCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colores, typography = Tipografia, content = content)
}
