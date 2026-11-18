package com.cerron.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerron.campusgps.data.*
import com.cerron.campusgps.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class MapaViewModel(private val ubicacionRepo: UbicacionRepository, private val lugaresRepo: LugaresRepository) : ViewModel() {
 private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
 val estado = _estado.asStateFlow()
 private val _lugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
 val lugares = _lugares.asStateFlow()
 private val _seleccionado = MutableStateFlow<Int?>(null)
 val seleccionado = _seleccionado.asStateFlow()
 fun buscar() {
  _estado.value = UbicacionUi.Buscando
  viewModelScope.launch {
   ubicacionRepo.obtenerPosicion().onSuccess {
    _estado.value = UbicacionUi.Lista(it)
   }.onFailure {
    _estado.value = UbicacionUi.Error(mensajeDe(it))
   }
  }
 }
 fun permisoDenegado() { _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás") }
 private fun mensajeDe(e: Throwable): String = when (e) {
  is SecurityException -> "Falta el permiso de ubicación"
  else -> e.message ?: "No se pudo obtener la ubicación"
 }
}
