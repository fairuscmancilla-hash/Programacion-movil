package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import com.tecsup.ayudacampus.ui.components.barraScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.data.local.entity.prioridadEnum
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.BurbujaComentario
import com.tecsup.ayudacampus.ui.components.EstadoChip
import com.tecsup.ayudacampus.ui.components.Estrellas
import com.tecsup.ayudacampus.ui.components.EstiloBoton
import com.tecsup.ayudacampus.ui.components.PrioridadChip
import com.tecsup.ayudacampus.ui.components.Seccion
import com.tecsup.ayudacampus.ui.components.StepperTicket
import com.tecsup.ayudacampus.ui.components.Tarjeta
import com.tecsup.ayudacampus.ui.components.fechaCorta
import com.tecsup.ayudacampus.ui.components.iconoCategoria
import com.tecsup.ayudacampus.ui.components.numeroTicket
import com.tecsup.ayudacampus.ui.components.tiempoRelativo
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel

@Composable
fun DetalleTicketScreen(vm: TicketDetalleViewModel, usuario: Usuario, onAtras: () -> Unit) {
    val detalle by vm.ticket.collectAsState()
    val d = detalle

    Column(Modifier.fillMaxSize().imePadding()) {
        AppTopBar(if (d != null) "Ticket ${numeroTicket(d.ticket.id)}" else "Ticket", onAtras = onAtras)
        if (d == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            return@Column
        }
        val estado = d.ticket.estadoEnum
        var calificacion by rememberSaveable { mutableIntStateOf(0) }
        var comentarioFinal by rememberSaveable { mutableStateOf("") }

        val estadoLista = rememberLazyListState()
        LazyColumn(
            Modifier.weight(1f).barraScroll(estadoLista).padding(horizontal = 16.dp),
            state = estadoLista,
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { CabeceraTicket(d) }
            if (estado == EstadoTicket.RESUELTO) item { AvisoResuelto() }
            item { StepperTicket(estado, Modifier.padding(vertical = 6.dp)) }
            item { Seccion("Conversación", Modifier.padding(top = 0.dp)) }
            if (d.comentarios.isEmpty()) {
                item { Text("Aún no hay comentarios.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
            items(d.comentarios, key = { it.id }) { c -> BurbujaComentario(c, esPropio = esPropio(c, usuario)) }

            when (estado) {
                EstadoTicket.RESUELTO -> item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Tarjeta {
                            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Califica la atención", style = MaterialTheme.typography.titleSmall)
                                Spacer(Modifier.height(8.dp))
                                Estrellas(calificacion, onCambio = { calificacion = it })
                                Text("$calificacion de 5 estrellas", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        AyudaTextField(comentarioFinal, { comentarioFinal = it }, etiqueta = "Comentario final (opcional)")
                        // Deshabilitado mientras la calificación sea 0
                        AyudaButton("Confirmar y cerrar", onClick = { vm.cerrar(calificacion, comentarioFinal) }, enabled = calificacion > 0)
                        AyudaButton("Reabrir ticket", onClick = { vm.reabrir() }, estilo = EstiloBoton.PELIGRO)
                    }
                }
                EstadoTicket.CERRADO -> item {
                    Tarjeta {
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Ticket cerrado", style = MaterialTheme.typography.titleSmall)
                            d.ticket.fechaCierre?.let {
                                Text("El ${fechaCorta(it)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Spacer(Modifier.height(8.dp))
                            Estrellas(d.ticket.calificacion ?: 0, tamano = 28.dp)
                            Text("Calificaste con ${d.ticket.calificacion ?: 0} de 5", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                else -> Unit
            }
        }
        // El campo de comentarios se oculta cuando el ticket está CERRADO
        if (estado != EstadoTicket.CERRADO) {
            CampoMensaje("Escribe un comentario...") { vm.comentar(it) }
        }
    }
}

fun esPropio(c: Comentario, usuario: Usuario) = c.autorId == usuario.id && c.autorRol == usuario.rol

@Composable
private fun CabeceraTicket(d: TicketConDetalle) {
    Tarjeta {
        Text(d.ticket.titulo, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            EstadoChip(d.ticket.estadoEnum)
            PrioridadChip(d.ticket.prioridadEnum)
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(iconoCategoria(d.categoria?.icono), null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.width(6.dp))
            Text(
                listOfNotNull(d.categoria?.nombre, d.ticket.ubicacion.ifBlank { null }, tiempoRelativo(d.ticket.fecha)).joinToString("  ·  "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AvisoResuelto() {
    Surface(shape = RoundedCornerShape(14.dp), color = EstadoResuelto.copy(alpha = 0.12f), modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.CheckCircle, null, tint = EstadoResuelto)
            Spacer(Modifier.width(10.dp))
            Column {
                Text("Soporte marcó este ticket como resuelto.", style = MaterialTheme.typography.titleSmall, color = EstadoResuelto)
                Text("¿El problema se solucionó?", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

/** Campo para escribir + botón enviar (lo usan el estudiante y Soporte). */
@Composable
fun CampoMensaje(placeholder: String, onEnviar: (String) -> Unit) {
    var texto by rememberSaveable { mutableStateOf("") }
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AyudaTextField(texto, { texto = it }, Modifier.weight(1f), placeholder = placeholder)
        Spacer(Modifier.width(8.dp))
        FilledIconButton(
            onClick = { onEnviar(texto); texto = "" },
            enabled = texto.isNotBlank(),
            shape = CircleShape,
            modifier = Modifier.size(48.dp),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
        }
    }
}
