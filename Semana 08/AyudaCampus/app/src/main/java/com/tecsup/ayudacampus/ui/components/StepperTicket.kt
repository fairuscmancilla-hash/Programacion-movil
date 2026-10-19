package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto

/** Abierto → En proceso → Resuelto → Cerrado */
@Composable
fun StepperTicket(estado: EstadoTicket, modifier: Modifier = Modifier) {
    val pasos = EstadoTicket.entries
    val actual = estado.ordinal
    val colores = MaterialTheme.colorScheme
    Row(modifier.fillMaxWidth()) {
        pasos.forEachIndexed { i, paso ->
            val hecho = i < actual || estado == EstadoTicket.CERRADO
            val esActual = i == actual && estado != EstadoTicket.CERRADO
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Linea(visible = i > 0, color = if (i <= actual) EstadoResuelto else colores.outlineVariant)
                    Box(
                        Modifier
                            .size(22.dp)
                            .background(
                                when {
                                    hecho -> EstadoResuelto
                                    esActual -> colores.primary
                                    else -> colores.surface
                                },
                                CircleShape
                            )
                            .border(2.dp, if (hecho || esActual) Color.Transparent else colores.outline, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            hecho -> Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            esActual -> Box(Modifier.size(8.dp).background(colores.onPrimary, CircleShape))
                        }
                    }
                    Linea(visible = i < pasos.lastIndex, color = if (i < actual) EstadoResuelto else colores.outlineVariant)
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    paso.etiqueta,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (esActual || hecho) FontWeight.Bold else FontWeight.Normal,
                    color = if (esActual || hecho) colores.onSurface else colores.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.Linea(visible: Boolean, color: Color) {
    Box(
        Modifier
            .weight(1f)
            .height(2.dp)
            .background(if (visible) color else Color.Transparent)
    )
}
