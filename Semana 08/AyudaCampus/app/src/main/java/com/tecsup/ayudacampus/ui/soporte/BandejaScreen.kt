package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import com.tecsup.ayudacampus.ui.components.barraScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.FiltrosEstado
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(vm: BandejaViewModel, onAtras: () -> Unit, onAtender: (Int) -> Unit) {
    val tickets by vm.tickets.collectAsState()
    val sla by vm.sla.collectAsState()
    val filtro by vm.filtro.collectAsState()

    Column(Modifier.fillMaxSize()) {
        AppTopBar("Tickets", onAtras = onAtras)
        FiltrosEstado(filtro, vm::filtrar, Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))
        if (tickets.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay tickets aquí", color = MaterialTheme.colorScheme.onSurfaceVariant)
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
