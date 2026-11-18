package com.cerron.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerron.campusgps.data.*
import com.cerron.campusgps.model.*
import com.cerron.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class MapaViewModel(private val ubicacionRepo: UbicacionRepository, private val lugaresRepo: LugaresRepository) : ViewModel() {
 private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
 val estado = _estado.asStateFlow()
 private val _lugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
 val lugares = _lugares.asStateFlow()
 private val _seleccionado = MutableStateFlow<Int?>(null)
 val seleccionado = _seleccionado.asStateFlow()
 // Cambia en cada selección para mover la cámara aunque se elija el mismo lugar otra vez.
 private val _solicitudCamara = MutableStateFlow(0)
 val solicitudCamara = _solicitudCamara.asStateFlow()
 val masCercano: StateFlow<Lugar?> = lugares.map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
  .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
 fun buscar() {
  _estado.value = UbicacionUi.Buscando
  viewModelScope.launch {
   ubicacionRepo.obtenerPosicion().onSuccess {
    actualizarDistancias(it)
    _seleccionado.value = null
    _estado.value = UbicacionUi.Lista(it)
   }.onFailure {
    _estado.value = UbicacionUi.Error(mensajeDe(it))
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
 fun permisoDenegado() { _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás") }
 private fun mensajeDe(e: Throwable): String = when (e) {
  is SecurityException -> "Falta el permiso de ubicación"
  else -> e.message ?: "No se pudo obtener la ubicación"
 }
}
