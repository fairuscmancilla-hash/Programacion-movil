package com.cerron.campusgps.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cerron.campusgps.model.*
import com.cerron.campusgps.viewmodel.MapaViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
 val estado by vm.estado.collectAsState()
 Scaffold(topBar = { TopAppBar(title = { Text("CampusGPS", fontWeight = FontWeight.Bold) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary)) }) { padding ->
  Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
   Button(onClick = { vm.buscar() }, enabled = estado != UbicacionUi.Buscando, modifier = Modifier.padding(top = 12.dp)) {
    if (estado == UbicacionUi.Buscando) { CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp); Spacer(Modifier.width(8.dp)) }
    Text(if (estado == UbicacionUi.Buscando) "Buscando…" else "Obtener mi ubicación", fontWeight = FontWeight.Bold)
   }
   when (val actual = estado) {
    UbicacionUi.Inicial -> Text("Pulsa el botón para empezar", Modifier.padding(top = 6.dp))
    is UbicacionUi.Lista -> {
     Text("Latitud: ${actual.posicion.latitud}", Modifier.padding(top = 4.dp), style = MaterialTheme.typography.bodySmall)
     Text("Longitud: ${actual.posicion.longitud}", style = MaterialTheme.typography.bodySmall)
    }
    is UbicacionUi.Error -> Surface(Modifier.fillMaxWidth().padding(top = 12.dp), color = MaterialTheme.colorScheme.errorContainer, shape = MaterialTheme.shapes.medium) {
     Text(actual.mensaje, Modifier.padding(12.dp), color = MaterialTheme.colorScheme.onErrorContainer)
    }
    else -> Unit
   }
  }
 }
}
