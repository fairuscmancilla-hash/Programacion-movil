package com.tecsup.ayudacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GraficoDona(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    tamano: Dp = 110.dp,
    grosor: Dp = 18.dp,
    textoCentro: String = "tickets"
) {
    val total = datos.sumOf { it.valor.toDouble() }.toFloat()
    val vacio = MaterialTheme.colorScheme.outlineVariant
    val primario = MaterialTheme.colorScheme.primary
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(tamano), contentAlignment = Alignment.Center) {
            Canvas(Modifier.size(tamano)) {
                val trazo = grosor.toPx()
                val arco = Size(size.width - trazo, size.height - trazo)
                val origen = Offset(trazo / 2, trazo / 2)
                if (total <= 0f) {
                    drawArc(vacio, 0f, 360f, false, origen, arco, style = Stroke(trazo))
                } else {
                    var inicio = -90f
                    datos.forEach { d ->
                        val barrido = 360f * d.valor / total
                        if (barrido > 0f) {
                            drawArc(
                                color = if (d.color == Color.Unspecified) primario else d.color,
                                startAngle = inicio,
                                sweepAngle = (barrido - 2f).coerceAtLeast(0.5f),
                                useCenter = false,
                                topLeft = origen, size = arco,
                                style = Stroke(trazo)
                            )
                        }
                        inicio += barrido
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("${total.toInt()}", style = MaterialTheme.typography.titleLarge)
                Text(textoCentro, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(Modifier.width(20.dp))
        // Leyenda
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            datos.forEach { d ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(12.dp)
                            .background(if (d.color == Color.Unspecified) primario else d.color, RoundedCornerShape(3.dp))
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(d.etiqueta, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                    Text("${d.valor.toInt()}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
