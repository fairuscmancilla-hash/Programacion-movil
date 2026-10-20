package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.data.local.entity.prioridadEnum
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.model.EstadoSla
import com.tecsup.ayudacampus.ui.theme.EstadoAbierto
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto
import com.tecsup.ayudacampus.ui.theme.PrioridadAlta
import com.tecsup.ayudacampus.ui.theme.color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer

@Composable
fun IconoCategoria(icono: String?, modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            iconoCategoria(icono), contentDescription = null,
            tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun TicketCard(
    detalle: TicketConDetalle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mostrarCliente: Boolean = false,
    sla: EstadoSla? = null
) {
    val t = detalle.ticket
    val colores = MaterialTheme.colorScheme
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colores.surface),
        border = BorderStroke(1.dp, colores.outlineVariant)
    ) {
        Row(Modifier.height(IntrinsicSize.Min)) {
            // Franja de color según la prioridad
            Box(
                Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(t.prioridadEnum.color())
            )
            Row(Modifier.padding(14.dp)) {
                IconoCategoria(detalle.categoria?.icono)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        t.titulo, style = MaterialTheme.typography.titleSmall,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    val detalleLinea = if (mostrarCliente) nombreCorto(detalle.cliente?.nombre) else t.ubicacion
                    Text(
                        "${numeroTicket(t.id)}  ·  $detalleLinea",
                        style = MaterialTheme.typography.bodySmall,
                        color = colores.onSurfaceVariant,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        EstadoChip(t.estadoEnum)
                        Spacer(Modifier.width(6.dp))
                        PrioridadChip(t.prioridadEnum)
                        Spacer(Modifier.weight(1f))
                        Text(
                            tiempoRelativo(t.fecha),
                            style = MaterialTheme.typography.bodySmall,
                            color = colores.onSurfaceVariant
                        )
                    }
                    sla?.let {
                        Spacer(Modifier.height(6.dp))
                        SlaChip(it)
                    }
                }
            }
        }
    }
}

/** Verde: a tiempo · Ámbar: queda menos del 25 % · Rojo: vencido */
@Composable
fun SlaChip(sla: EstadoSla, modifier: Modifier = Modifier) {
    val (texto, color) = when {
        sla.vencido -> "SLA vencido hace ${duracion(-sla.restanteMs)}" to PrioridadAlta
        sla.porVencer -> "SLA: quedan ${duracion(sla.restanteMs)}" to EstadoAbierto
        else -> "SLA: quedan ${duracion(sla.restanteMs)}" to EstadoResuelto
    }
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Outlined.Timer, null, tint = color, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(4.dp))
        Text("$texto (${sla.limiteHoras} h)", style = MaterialTheme.typography.labelSmall, color = color)
    }
}
