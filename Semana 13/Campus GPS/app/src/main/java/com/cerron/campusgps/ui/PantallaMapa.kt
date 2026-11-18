package com.cerron.campusgps.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.cerron.campusgps.BuildConfig
import com.cerron.campusgps.model.*
import com.cerron.campusgps.util.formatearCoordenada
import com.cerron.campusgps.viewmodel.MapaViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
 val estado by vm.estado.collectAsState()
 val lugares by vm.lugares.collectAsState()
 val seleccionado by vm.seleccionado.collectAsState()
 val contexto = LocalContext.current
 val lanzador = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultado ->
  if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
 }
 fun alPulsar() {
  if (listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION).any { ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED }) vm.buscar()
  else lanzador.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
 }
 Scaffold(topBar = { TopAppBar(title = { Text("CampusGPS", fontWeight = FontWeight.Bold) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary)) }) { padding ->
  val horizontal = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
  val mapa: @Composable (Modifier) -> Unit = { modificador ->
   if (BuildConfig.MAPS_CONFIGURADO) {
    MapaUbicacion(estado, lugares, seleccionado, modificador)
   } else {
    Box(modificador.background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
     Text("Configura la clave de Google Maps en local.properties", Modifier.padding(16.dp))
    }
   }
  }
  val controles: @Composable () -> Unit = {
   Button(onClick = { alPulsar() }, enabled = estado != UbicacionUi.Buscando, modifier = Modifier.padding(top = 12.dp)) {
    if (estado == UbicacionUi.Buscando) { CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp); Spacer(Modifier.width(8.dp)) }
    Text(if (estado == UbicacionUi.Buscando) "Buscando…" else "Obtener mi ubicación", fontWeight = FontWeight.Bold)
   }
   when (val actual = estado) {
    UbicacionUi.Inicial -> Text("Pulsa el botón para empezar", Modifier.padding(top = 6.dp))
    is UbicacionUi.Lista -> {
     Text("Latitud: ${formatearCoordenada(actual.posicion.latitud)}", Modifier.padding(top = 4.dp), style = MaterialTheme.typography.bodySmall)
     Text("Longitud: ${formatearCoordenada(actual.posicion.longitud)}", style = MaterialTheme.typography.bodySmall)
    }
    is UbicacionUi.Error -> Surface(Modifier.fillMaxWidth().padding(top = 12.dp), color = MaterialTheme.colorScheme.errorContainer, shape = MaterialTheme.shapes.medium) {
     Text(actual.mensaje, Modifier.padding(12.dp), color = MaterialTheme.colorScheme.onErrorContainer)
    }
    else -> Unit
   }
   ListaLugares(lugares, seleccionado, { vm.seleccionar(it) }, Modifier.fillMaxWidth().padding(top = 4.dp).height(220.dp))
  }
  if (horizontal) {
   Row(Modifier.fillMaxSize().padding(padding)) {
    mapa(Modifier.weight(1f).fillMaxHeight())
    Column(Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) { controles() }
   }
  } else {
   Column(Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {
    mapa(Modifier.fillMaxWidth().weight(1f))
    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) { controles() }
   }
  }
 }
}
