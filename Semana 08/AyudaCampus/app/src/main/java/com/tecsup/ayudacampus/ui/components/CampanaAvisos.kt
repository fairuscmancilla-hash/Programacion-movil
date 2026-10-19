package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.viewmodel.AvisosViewModel

/** Campana con el número de avisos sin leer; al tocarla muestra la lista. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampanaAvisos(vm: AvisosViewModel, onAbrirTicket: (Int) -> Unit) {
    val avisos by vm.avisos.collectAsState()
    val noLeidos by vm.noLeidos.collectAsState()
    val porNotificar by vm.porNotificar.collectAsState()
    val context = LocalContext.current
    var abierto by remember { mutableStateOf(false) }

    // Lo nuevo también sale como notificación del sistema
    LaunchedEffect(porNotificar) {
        if (porNotificar.isNotEmpty()) {
            porNotificar.forEach { publicarNotificacion(context, it) }
            vm.marcarNotificados(porNotificar)
        }
    }

    IconButton(onClick = { abierto = true }) {
        BadgedBox(badge = {
            if (noLeidos > 0) Badge { Text(if (noLeidos > 9) "9+" else "$noLeidos") }
        }) {
            Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones")
        }
    }

    if (abierto) {
        val cerrar = { abierto = false; vm.marcarLeidos() }
        ModalBottomSheet(onDismissRequest = cerrar, containerColor = MaterialTheme.colorScheme.surface) {
            Column(Modifier.padding(bottom = 24.dp)) {
                Text(
                    "Notificaciones", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(8.dp))
                if (avisos.isEmpty()) {
                    Text(
                        "No tienes notificaciones",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(20.dp)
                    )
                } else {
                    val estadoLista = rememberLazyListState()
                    LazyColumn(Modifier.heightIn(max = 460.dp).barraScroll(estadoLista), state = estadoLista) {
                        items(avisos, key = { "${it.ticketId}_${it.fecha}_${it.titulo}" }) { a ->
                            ListItem(
                                headlineContent = {
                                    Text(a.titulo, fontWeight = if (a.leido) FontWeight.Normal else FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                },
                                supportingContent = { Text(a.detalle, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                                trailingContent = { Text(tiempoRelativo(a.fecha), style = MaterialTheme.typography.bodySmall) },
                                leadingContent = {
                                    Box(
                                        Modifier.size(10.dp).background(
                                            if (a.leido) Color.Transparent else MaterialTheme.colorScheme.primary, CircleShape
                                        )
                                    )
                                },
                                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
                                modifier = Modifier.fillMaxWidth().clickable { cerrar(); onAbrirTicket(a.ticketId) }
                            )
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        }
                    }
                }
            }
        }
    }
}
