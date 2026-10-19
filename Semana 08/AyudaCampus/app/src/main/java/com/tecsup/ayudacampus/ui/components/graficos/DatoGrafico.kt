package com.tecsup.ayudacampus.ui.components.graficos

import androidx.compose.ui.graphics.Color

/** Un valor para los gráficos. Color.Unspecified = usa el color primario del tema. */
data class DatoGrafico(
    val etiqueta: String,
    val valor: Float,
    val color: Color = Color.Unspecified
)
