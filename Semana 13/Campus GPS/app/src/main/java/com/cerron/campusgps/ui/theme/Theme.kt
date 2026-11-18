package com.cerron.campusgps.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
val NavyCampus = Color(0xFF091C45)
val GlacierCampus = Color(0xFF62A7EA)
val LemonCampus = Color(0xFFF1EA1E)
val GlacierSuave = Color(0xFFE9F3FC)
val GlacierSeleccion = Color(0xFFC0DCF7)
val FondoCampus = Color(0xFFFDFBFF)
val FondoError = Color(0xFFFBE4E4)
val TextoError = Color(0xFF8C1D18)
@Composable
fun CampusGPSTheme(content: @Composable () -> Unit) {
 MaterialTheme(colorScheme = lightColorScheme(primary = NavyCampus, onPrimary = Color.White, secondary = GlacierCampus, onSecondary = NavyCampus, tertiary = LemonCampus, onTertiary = NavyCampus, background = FondoCampus, surface = FondoCampus, errorContainer = FondoError, onErrorContainer = TextoError), content = content)
}
