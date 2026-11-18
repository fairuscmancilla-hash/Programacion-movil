package com.cerron.campusgps.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.cerron.campusgps.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
@Composable
fun MapaUbicacion(estado: UbicacionUi, lugares: List<LugarConDistancia>, seleccionado: Int?, solicitudCamara: Int, modifier: Modifier = Modifier) {
 val camara = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f) }
 var cargado by remember { mutableStateOf(false) }
 LaunchedEffect(estado, cargado) {
  if (cargado && estado is UbicacionUi.Lista) camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(estado.posicion.latitud, estado.posicion.longitud), 16f))
 }
 LaunchedEffect(seleccionado, solicitudCamara, cargado) {
  if (cargado) lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar?.let {
   camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitud, it.longitud), 17f))
  }
 }
 val cercano = lugares.firstOrNull { it.metros != null }?.lugar?.id
 val ajustes = remember { MapUiSettings(zoomControlsEnabled = false, mapToolbarEnabled = false) }
 GoogleMap(modifier = modifier, cameraPositionState = camara, uiSettings = ajustes, onMapLoaded = { cargado = true }) {
  if (estado is UbicacionUi.Lista) {
   val propio = rememberUpdatedMarkerState(LatLng(estado.posicion.latitud, estado.posicion.longitud))
   LaunchedEffect(estado, cargado) { if (cargado && seleccionado == null) propio.showInfoWindow() }
   Marker(state = propio, title = "Estás aquí")
  }
  lugares.forEach { item -> key(item.lugar.id) {
   val lugar = item.lugar
   val marcador = rememberUpdatedMarkerState(LatLng(lugar.latitud, lugar.longitud))
   LaunchedEffect(seleccionado, solicitudCamara, cargado) { if (cargado && seleccionado == lugar.id) marcador.showInfoWindow() }
   Marker(state = marcador, title = lugar.nombre, snippet = lugar.categoria,
    icon = BitmapDescriptorFactory.defaultMarker(if (lugar.id == cercano) BitmapDescriptorFactory.HUE_GREEN else BitmapDescriptorFactory.HUE_AZURE))
  } }
 }
}
