package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

/** Pista + barra de scroll a la derecha de una LazyColumn. Solo aparece si el contenido no cabe. */
fun Modifier.barraScroll(estado: LazyListState): Modifier = composed {
    val colorBarra = MaterialTheme.colorScheme.primary.copy(alpha = 0.75f)
    val colorPista = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)
    drawWithContent {
        drawContent()
        val info = estado.layoutInfo
        val total = info.totalItemsCount
        val visibles = info.visibleItemsInfo
        if (total == 0 || visibles.isEmpty() || (!estado.canScrollForward && !estado.canScrollBackward)) return@drawWithContent

        // Fracción visible y posición, estimadas con el tamaño promedio de los ítems
        val promedio = visibles.sumOf { it.size }.toFloat() / visibles.size
        val contenido = (promedio * total).coerceAtLeast(size.height)
        val desplazamiento = estado.firstVisibleItemIndex * promedio + estado.firstVisibleItemScrollOffset
        dibujarBarra(colorPista, colorBarra, size.height / contenido, desplazamiento / (contenido - size.height))
    }
}

/** Lo mismo para una Column con verticalScroll(estado). */
fun Modifier.barraScroll(estado: ScrollState): Modifier = composed {
    val colorBarra = MaterialTheme.colorScheme.primary.copy(alpha = 0.75f)
    val colorPista = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)
    drawWithContent {
        drawContent()
        if (estado.maxValue <= 0 || estado.maxValue == Int.MAX_VALUE) return@drawWithContent
        val contenido = size.height + estado.maxValue
        dibujarBarra(colorPista, colorBarra, size.height / contenido, estado.value.toFloat() / estado.maxValue)
    }
}

/** verticalScroll + barra visible, en un solo modificador. */
fun Modifier.scrollConBarra(): Modifier = composed {
    val estado = rememberScrollState()
    barraScroll(estado).verticalScroll(estado)
}

private fun DrawScope.dibujarBarra(colorPista: Color, colorBarra: Color, fraccionVisible: Float, progreso: Float) {
    val ancho = 6.dp.toPx()
    val x = size.width - ancho - 2.dp.toPx()
    val radio = CornerRadius(ancho / 2, ancho / 2)
    val altoBarra = (size.height * fraccionVisible).coerceIn(32.dp.toPx(), size.height)
    val y = (size.height - altoBarra) * progreso.coerceIn(0f, 1f)
    drawRoundRect(colorPista, Offset(x, 0f), Size(ancho, size.height), radio)
    drawRoundRect(colorBarra, Offset(x, y), Size(ancho, altoBarra), radio)
}
