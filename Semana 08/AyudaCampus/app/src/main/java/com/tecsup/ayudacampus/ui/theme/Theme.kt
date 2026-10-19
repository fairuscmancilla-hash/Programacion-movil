package com.tecsup.ayudacampus.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tecsup.ayudacampus.model.Rol

private val EstudianteClaro = lightColorScheme(
    primary = Cian, onPrimary = Color.White,
    primaryContainer = CianContenedor, onPrimaryContainer = CianTinta,
    secondary = Cian, onSecondary = Color.White,
    secondaryContainer = CianContenedor, onSecondaryContainer = CianTinta,
    background = FondoEstudiante, onBackground = Tinta,
    surface = Color.White, onSurface = Tinta,
    surfaceVariant = Color(0xFFF1F5F9), onSurfaceVariant = TintaSuave,
    surfaceContainerLowest = Color.White, surfaceContainerLow = Color.White,
    surfaceContainer = Color.White, surfaceContainerHigh = Color(0xFFF1F5F9),
    surfaceContainerHighest = Color(0xFFE2E8F0),
    outline = Color(0xFFCBD5E1), outlineVariant = Borde,
    error = PrioridadAlta
)

private val SoporteClaro = lightColorScheme(
    primary = Violeta, onPrimary = Color.White,
    primaryContainer = VioletaContenedor, onPrimaryContainer = VioletaTinta,
    secondary = Violeta, onSecondary = Color.White,
    secondaryContainer = VioletaContenedor, onSecondaryContainer = VioletaTinta,
    background = FondoSoporte, onBackground = Tinta,
    surface = Color.White, onSurface = Tinta,
    surfaceVariant = Color(0xFFF3F0FA), onSurfaceVariant = TintaSuave,
    surfaceContainerLowest = Color.White, surfaceContainerLow = Color.White,
    surfaceContainer = Color.White, surfaceContainerHigh = Color(0xFFF3F0FA),
    surfaceContainerHighest = Color(0xFFE7E2F3),
    outline = Color(0xFFCBD5E1), outlineVariant = Borde,
    error = PrioridadAlta
)

private val EstudianteOscuro = darkColorScheme(
    primary = CianClaro, onPrimary = Color(0xFF003544),
    primaryContainer = Color(0xFF155E75), onPrimaryContainer = CianContenedor,
    secondary = CianClaro, onSecondary = Color(0xFF003544),
    secondaryContainer = Color(0xFF155E75), onSecondaryContainer = CianContenedor,
    background = Color(0xFF0A1416), onBackground = Color(0xFFE2E8F0),
    surface = Color(0xFF12201F), onSurface = Color(0xFFE2E8F0),
    surfaceVariant = Color(0xFF1C2C2F), onSurfaceVariant = Color(0xFF94A3B8),
    surfaceContainerLowest = Color(0xFF0A1416), surfaceContainerLow = Color(0xFF12201F),
    surfaceContainer = Color(0xFF12201F), surfaceContainerHigh = Color(0xFF1C2C2F),
    surfaceContainerHighest = Color(0xFF263538),
    outline = Color(0xFF475569), outlineVariant = Color(0xFF2A3A3D),
    error = Color(0xFFF87171)
)

private val SoporteOscuro = darkColorScheme(
    primary = VioletaClaro, onPrimary = Color(0xFF2E0B6B),
    primaryContainer = Color(0xFF4C1D95), onPrimaryContainer = VioletaContenedor,
    secondary = VioletaClaro, onSecondary = Color(0xFF2E0B6B),
    secondaryContainer = Color(0xFF4C1D95), onSecondaryContainer = VioletaContenedor,
    background = Color(0xFF110D1A), onBackground = Color(0xFFE5E1F0),
    surface = Color(0xFF1A1526), onSurface = Color(0xFFE5E1F0),
    surfaceVariant = Color(0xFF261F35), onSurfaceVariant = Color(0xFFA59DB8),
    surfaceContainerLowest = Color(0xFF110D1A), surfaceContainerLow = Color(0xFF1A1526),
    surfaceContainer = Color(0xFF1A1526), surfaceContainerHigh = Color(0xFF261F35),
    surfaceContainerHighest = Color(0xFF302842),
    outline = Color(0xFF5B5470), outlineVariant = Color(0xFF332B45),
    error = Color(0xFFF87171)
)

fun esquemaDe(rol: Rol, oscuro: Boolean): ColorScheme = when (rol) {
    Rol.CLIENTE -> if (oscuro) EstudianteOscuro else EstudianteClaro
    Rol.ADMIN -> if (oscuro) SoporteOscuro else SoporteClaro
}

@Composable
fun AyudaTheme(
    rol: Rol = Rol.CLIENTE,
    oscuro: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = esquemaDe(rol, oscuro),
        typography = Typography,
        content = content
    )
}
