package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.FiltrosEstado
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.ui.components.barraScroll
import com.tecsup.ayudacampus.ui.components.iconoCategoria
import com.tecsup.ayudacampus.ui.theme.color
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(vm: BandejaViewModel, onAtras: () -> Unit, onAtender: (Int) -> Unit) {
    val tickets by vm.tickets.collectAsState()
    val sla by vm.sla.collectAsState()
    val filtro by vm.filtro.collectAsState()
    val prioridad by vm.prioridad.collectAsState()
    val categoriaId by vm.categoriaId.collectAsState()
    val texto by vm.texto.collectAsState()
    val categorias by vm.categorias.collectAsState()
    val hayFiltros by vm.hayFiltros.collectAsState()

    Column(Modifier.fillMaxSize()) {
        AppTopBar("Tickets", onAtras = onAtras)
        Column(Modifier.padding(horizontal = 16.dp)) {
            AyudaTextField(texto, vm::buscar, placeholder = "Buscar por título, número o cliente...", icono = Icons.Outlined.Search)
            Spacer(Modifier.height(6.dp))
            FiltrosEstado(filtro, vm::filtrar)
            // Prioridad
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChipFiltro("Toda prioridad", prioridad == null, Color.Unspecified) { vm.filtrarPrioridad(null) }
                Prioridad.entries.reversed().forEach { p ->
                    ChipFiltro(p.etiqueta, prioridad == p, p.color()) { vm.filtrarPrioridad(p) }
                }
            }
            // Categoría
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ChipFiltro("Toda categoría", categoriaId == null, Color.Unspecified) { vm.filtrarCategoria(null) }
                categorias.forEach { c ->
                    ChipFiltro(c.nombre, categoriaId == c.id, Color.Unspecified, icono = c.icono) { vm.filtrarCategoria(c.id) }
                }
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${tickets.size} ticket(s)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                if (hayFiltros) {
                    TextButton(onClick = vm::limpiarFiltros) { Text("Limpiar filtros") }
                }
            }
        }
        if (tickets.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay tickets con estos filtros", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            val estadoLista = rememberLazyListState()
            // Si entra un ticket nuevo arriba (o cambia el filtro), sube al inicio
            val primero = tickets.first().ticket.id
            LaunchedEffect(primero) { estadoLista.scrollToItem(0) }
            LazyColumn(
                state = estadoLista,
                modifier = Modifier.weight(1f).barraScroll(estadoLista),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(tickets, key = { it.ticket.id }) { d ->
                    TicketCard(d, onClick = { onAtender(d.ticket.id) }, mostrarCliente = true, sla = sla[d.ticket.id])
                }
            }
        }
    }
}

/** Chip de filtro; si se pasa un color (prioridad), el chip elegido toma ese color. */
@Composable
private fun ChipFiltro(texto: String, elegido: Boolean, color: Color, icono: String? = null, onClick: () -> Unit) {
    val fondo = if (color == Color.Unspecified) MaterialTheme.colorScheme.primary else color
    val tinta = if (color == Color.Unspecified) MaterialTheme.colorScheme.onPrimary else Color.White
    FilterChip(
        selected = elegido,
        onClick = onClick,
        label = { Text(texto) },
        leadingIcon = icono?.let { { Icon(iconoCategoria(it), null, Modifier.size(16.dp)) } },
        shape = RoundedCornerShape(50),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = fondo,
            selectedLabelColor = tinta,
            selectedLeadingIconColor = tinta,
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}
