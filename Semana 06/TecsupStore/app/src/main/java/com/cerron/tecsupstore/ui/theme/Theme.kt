package com.cerron.tecsupstore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TecsupColorScheme = lightColorScheme(

    primary = MoradoPrincipal,
    onPrimary = Blanco,

    primaryContainer = MoradoSeleccionado,
    onPrimaryContainer = MoradoOscuro,

    secondary = MoradoMedio,
    onSecondary = Blanco,

    secondaryContainer = MoradoClaro,
    onSecondaryContainer = MoradoOscuro,

    background = FondoApp,
    onBackground = TextoPrincipal,

    surface = FondoApp,
    onSurface = TextoPrincipal,

    surfaceVariant = FondoTarjeta,
    onSurfaceVariant = TextoPrincipal,

    outline = MoradoMedio
)

@Composable
fun TecsupstoreTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = TecsupColorScheme,
        typography = Typography,
        content = content
    )
}
