package com.cerron.centrotareas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaClaro = lightColorScheme(
    primary = NaranjaBoton,
    onPrimary = Blanco,
    primaryContainer = NaranjaClaro,
    onPrimaryContainer = Marino,
    secondary = Marino,
    onSecondary = Blanco,
    secondaryContainer = AzulHielo,
    onSecondaryContainer = Marino,
    background = Crema,
    onBackground = Marino,
    surface = Blanco,
    onSurface = Marino,
    surfaceVariant = AzulHielo,
    onSurfaceVariant = TextoSuave,
    outline = BordeSuave,
    error = RojoError,
    errorContainer = RojoFondo,
    onErrorContainer = RojoTexto
)

@Composable
fun CentroTareasTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = EsquemaClaro, content = content)
}
