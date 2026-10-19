package com.tecsup.ayudacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Barras verticales; la barra mayor se resalta con el color primario. */
@Composable
fun GraficoBarras(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    alto: Dp = 150.dp
) {
    val medidor = rememberTextMeasurer()
    val primario = MaterialTheme.colorScheme.primary
    val tinta = MaterialTheme.colorScheme.onSurface
    val tintaSuave = MaterialTheme.colorScheme.onSurfaceVariant
    val maximo = (datos.maxOfOrNull { it.valor } ?: 0f).coerceAtLeast(1f)
    val estiloValor = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = tinta)
    val estiloEtiqueta = TextStyle(fontSize = 10.sp, color = tintaSuave)

    Canvas(modifier.fillMaxWidth().height(alto)) {
        if (datos.isEmpty()) return@Canvas
        val zonaEtiqueta = 18.dp.toPx()
        val zonaValor = 16.dp.toPx()
        val altoUtil = size.height - zonaEtiqueta - zonaValor
        val ancho = size.width / datos.size
        val anchoBarra = ancho * 0.55f

        datos.forEachIndexed { i, d ->
            val h = altoUtil * (d.valor / maximo)
            val x = i * ancho + (ancho - anchoBarra) / 2
            val y = zonaValor + (altoUtil - h)
            val color = when {
                d.color != Color.Unspecified -> d.color
                d.valor == maximo && d.valor > 0f -> primario
                else -> primario.copy(alpha = 0.35f)
            }
            drawRoundRect(color, Offset(x, y), Size(anchoBarra, h.coerceAtLeast(2f)), CornerRadius(6f, 6f))

            val valor = medidor.measure("${d.valor.toInt()}", estiloValor)
            drawText(valor, topLeft = Offset(i * ancho + (ancho - valor.size.width) / 2, y - valor.size.height - 2f))

            val etiqueta = medidor.measure(
                d.etiqueta, estiloEtiqueta, maxLines = 1, overflow = TextOverflow.Ellipsis,
                constraints = androidx.compose.ui.unit.Constraints(maxWidth = ancho.toInt())
            )
            drawText(etiqueta, topLeft = Offset(i * ancho + (ancho - etiqueta.size.width) / 2, size.height - zonaEtiqueta + 4f))
        }
    }
}
