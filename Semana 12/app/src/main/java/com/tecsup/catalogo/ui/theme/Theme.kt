package com.tecsup.catalogo.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
// Colores muestreados directamente de la paleta proporcionada.
val Berenjena = Color(0xFF2D1A48)
val Lavanda = Color(0xFFD0C6DE)
val Amarillo = Color(0xFFF5C346)
val Morado = Berenjena
val Fondo = lerp(Lavanda, Color.White, 0.60f)
val Superficie = lerp(Lavanda, Color.White, 0.72f)
val BerenjenaOscura = lerp(Berenjena, Color.Black, 0.24f)
@Composable fun NexioTheme(content: @Composable () -> Unit) {
 MaterialTheme(colorScheme = lightColorScheme(primary=Berenjena, onPrimary=Color.White, secondary=Berenjena, secondaryContainer=Lavanda, onSecondaryContainer=Berenjena, tertiary=Amarillo, onTertiary=Berenjena, background=Fondo, onBackground=Berenjena, surface=Superficie, onSurface=Berenjena, surfaceVariant=Lavanda), content=content)
}
