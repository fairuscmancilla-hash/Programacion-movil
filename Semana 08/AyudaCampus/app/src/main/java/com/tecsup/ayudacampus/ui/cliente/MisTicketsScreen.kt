package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import com.tecsup.ayudacampus.ui.components.barraScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.FiltrosEstado
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel

@Composable
fun MisTicketsScreen(
    vm: MisTicketsViewModel,
    usuario: Usuario,
    onAbrir: (Int) -> Unit,
    onNuevo: () -> Unit,
    campana: @Composable () -> Unit = {}
) {
    val tickets by vm.tickets.collectAsState()
    val sla by vm.sla.collectAsState()
    val filtro by vm.filtro.collectAsState()
    val busqueda by vm.busqueda.collectAsState()
    val resumen by vm.resumen.collectAsState()

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Mis tickets", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        "Hola, ${usuario.nombre.substringBefore(" ")} · ${resumen.abiertos} abiertos",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                campana()
            }
            Spacer(Modifier.height(14.dp))
            AyudaTextField(busqueda, vm::buscar, placeholder = "Buscar por título o número...", icono = Icons.Outlined.Search)
            Spacer(Modifier.height(8.dp))
            FiltrosEstado(filtro, vm::filtrar)
            Spacer(Modifier.height(8.dp))

            if (tickets.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Outlined.Inbox, null, modifier = Modifier.size(56.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("No tienes tickets aquí", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                val estadoLista = rememberLazyListState()
                // Si llega un ticket nuevo arriba, sube al inicio para que se vea
                val primero = tickets.first().ticket.id
                LaunchedEffect(primero) { estadoLista.scrollToItem(0) }
                LazyColumn(
                    state = estadoLista,
                    modifier = Modifier.weight(1f).barraScroll(estadoLista),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tickets, key = { it.ticket.id }) { d ->
                        TicketCard(d, onClick = { onAbrir(d.ticket.id) }, sla = sla[d.ticket.id])
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = onNuevo,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Nuevo ticket")
        }
    }
}
