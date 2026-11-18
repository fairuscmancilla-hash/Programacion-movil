package com.cerron.campusgps.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.cerron.campusgps.BuildConfig
import com.cerron.campusgps.model.*
import com.cerron.campusgps.util.formatearCoordenada
import com.cerron.campusgps.util.formatearDistancia
import com.cerron.campusgps.viewmodel.MapaViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
 val estado by vm.estado.collectAsState()
 val lugares by vm.lugares.collectAsState()
 val seleccionado by vm.seleccionado.collectAsState()
 val solicitudCamara by vm.solicitudCamara.collectAsState()
 val cercano by vm.masCercano.collectAsState()
 val categoria by vm.categoria.collectAsState()
 val radio by vm.radio.collectAsState()
 val ultimo by vm.ultimoLugar.collectAsState()
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
  val botonUbicacion: @Composable () -> Unit = {
   Button(onClick = { alPulsar() }, enabled = estado != UbicacionUi.Buscando, modifier = Modifier.padding(top = 12.dp)) {
    if (estado == UbicacionUi.Buscando) { CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp); Spacer(Modifier.width(8.dp)) }
    Text(if (estado == UbicacionUi.Buscando) "Buscando…" else "Obtener mi ubicación", fontWeight = FontWeight.Bold)
   }
  }
  val mapa: @Composable (Modifier) -> Unit = { modificador ->
   if (BuildConfig.MAPS_CONFIGURADO) {
    MapaUbicacion(estado, lugares, seleccionado, solicitudCamara, radio, modificador)
   } else {
    Box(modificador.background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
     Text("Configura la clave de Google Maps en local.properties", Modifier.padding(16.dp))
    }
   }
  }
  val controles: @Composable () -> Unit = {
   botonUbicacion()
   when (val actual = estado) {
    UbicacionUi.Inicial -> Text("Pulsa el botón para empezar", Modifier.padding(top = 6.dp))
    is UbicacionUi.Lista -> {
     Text("Latitud: ${formatearCoordenada(actual.posicion.latitud)}", Modifier.padding(top = 4.dp), style = MaterialTheme.typography.bodySmall)
     Text("Longitud: ${formatearCoordenada(actual.posicion.longitud)}", style = MaterialTheme.typography.bodySmall)
    }
    else -> Unit
   }
   OutlinedButton(onClick = { vm.seleccionar(cercano?.id) }, enabled = cercano != null, modifier = Modifier.padding(top = 4.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.tertiary, contentColor = MaterialTheme.colorScheme.onTertiary),
    border = BorderStroke(2.dp, if (cercano != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))) {
    Text("Ir al más cercano", fontWeight = FontWeight.Bold)
   }
   cercano?.let { lugar ->
    val metros = lugares.firstOrNull { it.lugar.id == lugar.id }?.metros
    Text(buildAnnotatedString {
     withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Más cercano: ") }
     append("${lugar.nombre} (${metros?.let(::formatearDistancia) ?: "Sin calcular"})")
    }, Modifier.padding(top = 4.dp), style = MaterialTheme.typography.bodyMedium)
   }
   ultimo?.let { Text("Último lugar visitado: ${it.nombre}", style = MaterialTheme.typography.bodySmall) }
   SelectorRadio(vm.radiosDisponibles, radio, vm::seleccionarRadio)
   FiltroCategorias(vm.categorias, categoria, vm::seleccionarCategoria)
   ListaLugares(lugares, seleccionado, { vm.seleccionar(it) }, Modifier.fillMaxWidth().height(170.dp))
  }
  val error = estado as? UbicacionUi.Error
  if (error != null) {
   Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
    botonUbicacion()
    Spacer(Modifier.height(24.dp))
    MensajeError(error, abrirAjustes = {
     contexto.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", contexto.packageName, null)))
    }, reintentar = { alPulsar() })
   }
  } else if (horizontal) {
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
