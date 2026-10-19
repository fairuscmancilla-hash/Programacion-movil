package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.theme.color

@Composable
fun Etiqueta(texto: String, color: Color, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(50), color = color.copy(alpha = 0.15f)) {
        Text(
            texto,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun EstadoChip(estado: EstadoTicket, modifier: Modifier = Modifier) =
    Etiqueta(estado.etiqueta, estado.color(), modifier)

/** Chips Todos / Abiertos / En proceso / Resueltos. null = Todos (incluye CERRADOS). */
@Composable
fun FiltrosEstado(
    seleccion: EstadoTicket?,
    onSeleccion: (EstadoTicket?) -> Unit,
    modifier: Modifier = Modifier
) {
    val opciones = listOf(null, EstadoTicket.ABIERTO, EstadoTicket.EN_PROCESO, EstadoTicket.RESUELTO)
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        opciones.forEach { op ->
            FilterChip(
                selected = seleccion == op,
                onClick = { onSeleccion(op) },
                label = { Text(op?.plural ?: "Todos") },
                shape = RoundedCornerShape(50),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}
