package com.cerron.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerron.campusgps.data.*
import com.cerron.campusgps.model.*
import com.cerron.campusgps.util.distanciaMetros
import com.cerron.campusgps.util.filtrarPorCategoria
import com.cerron.campusgps.util.marcarDentroDelRadio
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class MapaViewModel(private val ubicacionRepo: UbicacionRepository, private val lugaresRepo: LugaresRepository) : ViewModel() {
 private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
 val estado = _estado.asStateFlow()
 private val _lugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
 val categorias: List<String> = lugaresRepo.obtenerLugares().map { it.categoria }.distinct()
 private val _categoria = MutableStateFlow<String?>(null)
 val categoria = _categoria.asStateFlow()
 val radiosDisponibles = listOf(100, 200, 500)
 private val _radio = MutableStateFlow(200)
 val radio = _radio.asStateFlow()
 val lugares: StateFlow<List<LugarConDistancia>> = combine(_lugares, _categoria, _radio) { lista, categoria, radio -> marcarDentroDelRadio(filtrarPorCategoria(lista, categoria), radio) }
  .stateIn(viewModelScope, SharingStarted.Eagerly, _lugares.value)
 private val _seleccionado = MutableStateFlow<Int?>(null)
 val seleccionado = _seleccionado.asStateFlow()
 // Cambia en cada selección para mover la cámara aunque se elija el mismo lugar otra vez.
 private val _solicitudCamara = MutableStateFlow(0)
 val solicitudCamara = _solicitudCamara.asStateFlow()
 val masCercano: StateFlow<Lugar?> = lugares.map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
  .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
 fun buscar() {
  if (_estado.value == UbicacionUi.Buscando) return
  if (!ubicacionRepo.playServicesDisponible()) {
   _estado.value = UbicacionUi.Error("Este dispositivo no tiene Google Play Services", TipoError.SIN_PLAY_SERVICES)
   return
  }
  _estado.value = UbicacionUi.Buscando
  viewModelScope.launch {
   ubicacionRepo.obtenerPosicion().onSuccess {
    actualizarDistancias(it)
    _seleccionado.value = null
    _estado.value = UbicacionUi.Lista(it)
   }.onFailure {
    _estado.value = when (it) {
     is SecurityException -> UbicacionUi.Error("Falta el permiso de ubicación", TipoError.SIN_PERMISO)
     is GpsNoDisponibleException -> UbicacionUi.Error("No se pudo obtener la ubicación.\nActiva el GPS e intenta de nuevo", TipoError.GPS_APAGADO)
     else -> UbicacionUi.Error(it.message ?: "No se pudo obtener la ubicación", TipoError.OTRO)
    }
   }
  }
 }
 private fun actualizarDistancias(pos: Posicion) {
  _lugares.value = lugaresRepo.obtenerLugares().map { LugarConDistancia(it, distanciaMetros(pos, it)) }.sortedBy { it.metros }
 }
 fun seleccionar(id: Int?) {
  _seleccionado.value = id
  _solicitudCamara.update { it + 1 }
 }
 fun seleccionarCategoria(nueva: String?) {
  _categoria.value = nueva
  val sigueVisible = filtrarPorCategoria(_lugares.value, nueva).any { it.lugar.id == _seleccionado.value }
  if (!sigueVisible) _seleccionado.value = null
 }
 fun seleccionarRadio(metros: Int) { _radio.value = metros }
 fun permisoDenegado() { _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás", TipoError.SIN_PERMISO) }
}
