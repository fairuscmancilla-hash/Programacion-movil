package com.tecsup.mibodega.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ---------------------------------------------
// TEMA CLARO
// ---------------------------------------------

private val BodegaLightColorScheme = lightColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    secondary = AzulEnlace,
    background = Blanco,
    onBackground = AzulTexto,
    surface = Blanco,
    onSurface = AzulTexto,
    surfaceVariant = GrisClaro,
    onSurfaceVariant = GrisTexto,
    outline = GrisBorde,
    error = RojoPrecio
)

// ---------------------------------------------
// TEMA OSCURO
// ---------------------------------------------

private val BodegaDarkColorScheme = darkColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    secondary = AzulEnlace,

    background = Color(0xFF121212),
    onBackground = Color(0xFFF5F5F5),

    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFF5F5F5),

    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFBDBDBD),

    outline = Color(0xFF616161),

    error = RojoPrecio
)

// ---------------------------------------------
// TEMA GENERAL DE LA APP
// ---------------------------------------------

@Composable
fun BodegaTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colores = if (darkTheme) {
        BodegaDarkColorScheme
    } else {
        BodegaLightColorScheme
    }

    MaterialTheme(
        colorScheme = colores,
        typography = BodegaTypography,
        content = content
    )
}

