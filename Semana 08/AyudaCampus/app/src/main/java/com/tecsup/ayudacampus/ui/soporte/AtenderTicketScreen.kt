package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import com.tecsup.ayudacampus.ui.components.barraScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.cliente.CampoMensaje
import com.tecsup.ayudacampus.ui.cliente.esPropio
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.BurbujaComentario
import com.tecsup.ayudacampus.ui.components.Estrellas
import com.tecsup.ayudacampus.ui.components.Seccion
import com.tecsup.ayudacampus.ui.components.SelectorPrioridad
import com.tecsup.ayudacampus.ui.components.Tarjeta
import com.tecsup.ayudacampus.ui.components.numeroTicket
import com.tecsup.ayudacampus.ui.theme.color
import com.tecsup.ayudacampus.viewmodel.AtenderViewModel

@Composable
fun AtenderTicketScreen(vm: AtenderViewModel, usuario: Usuario, onAtras: () -> Unit) {
    val detalle by vm.ticket.collectAsState()
    val estadoSel by vm.estado.collectAsState()
    val prioridadSel by vm.prioridad.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(mensaje) {
        mensaje?.let { snackbar.showSnackbar(it); vm.limpiarMensaje() }
    }

    val d = detalle
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().imePadding()) {
            AppTopBar(if (d != null) "Atender ticket ${numeroTicket(d.ticket.id)}" else "Atender ticket", onAtras = onAtras)
            if (d == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                return@Column
            }
            val cerrado = d.ticket.estadoEnum == EstadoTicket.CERRADO

            val estadoLista = rememberLazyListState()
            LazyColumn(
                Modifier.weight(1f).barraScroll(estadoLista).padding(horizontal = 16.dp),
                state = estadoLista,
                contentPadding = PaddingValues(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Tarjeta {
                        Text(d.ticket.titulo, style = MaterialTheme.typography.titleMedium)
                        Text(
                            listOfNotNull(d.cliente?.nombre, d.ticket.ubicacion.ifBlank { null }, d.categoria?.nombre).joinToString("  ·  "),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(d.ticket.descripcion, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                item {
                    Seccion("Estado", Modifier.padding(top = 0.dp))
                    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        EstadoTicket.entries.forEach { e ->
                            FilterChip(
                                selected = estadoSel == e,
                                onClick = { vm.cambiarEstado(e) },
                                // CERRADO deshabilitado: solo lo cierra el estudiante
                                enabled = !cerrado && e in AtenderViewModel.ESTADOS_SOPORTE,
                                label = { Text(e.etiqueta) },
                                shape = RoundedCornerShape(50),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = e.color(),
                                    selectedLabelColor = Color.White,
                                    disabledSelectedContainerColor = e.color().copy(alpha = 0.5f),
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                }
                item {
                    Seccion("Prioridad", Modifier.padding(top = 0.dp))
                    prioridadSel?.let { SelectorPrioridad(it, vm::cambiarPrioridad, enabled = !cerrado) }
                }
                if (cerrado) {
                    item {
                        Tarjeta {
                            Text("Ticket cerrado por el estudiante", style = MaterialTheme.typography.titleSmall)
                            Spacer(Modifier.height(4.dp))
                            Estrellas(d.ticket.calificacion ?: 0, tamano = 24.dp)
                        }
                    }
                }
                item { Seccion("Conversación", Modifier.padding(top = 4.dp)) }
                items(d.comentarios, key = { it.id }) { c -> BurbujaComentario(c, esPropio = esPropio(c, usuario)) }
            }
            if (!cerrado) {
                CampoMensaje("Responder al cliente...") { vm.responder(it) }
                AyudaButton(
                    "Guardar cambios", onClick = vm::guardar,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                )
            }
        }
        SnackbarHost(snackbar, Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp))
    }
}
