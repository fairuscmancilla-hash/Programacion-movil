package com.tecsup.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaClaro = lightColorScheme(
    primary = DeepNavy,
    onPrimary = SoftIvory,
    primaryContainer = NavySuave,
    onPrimaryContainer = DeepNavy,
    secondary = AntiqueGold,
    onSecondary = Blanco,
    secondaryContainer = OroClaro,
    onSecondaryContainer = OroOscuro,
    background = SoftIvory,
    onBackground = DeepNavy,
    surface = Blanco,
    onSurface = DeepNavy,
    surfaceVariant = NavySuave,
    onSurfaceVariant = TextoSuave,
    outline = Borde,
    error = RojoError,
    onError = Blanco,
    errorContainer = RojoFondo,
    onErrorContainer = RojoTexto
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = EsquemaClaro, content = content)
}
