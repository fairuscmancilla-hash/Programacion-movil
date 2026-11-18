package com.cerron.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cerron.campusgps.data.LugaresRepository
import com.cerron.campusgps.data.UbicacionRepository
import com.cerron.campusgps.data.UltimoLugarRepository
class CampusViewModelFactory(private val ubicacion: UbicacionRepository, private val lugares: LugaresRepository, private val ultimoLugar: UltimoLugarRepository) : ViewModelProvider.Factory {
 override fun <T : ViewModel> create(modelClass: Class<T>): T {
  require(modelClass.isAssignableFrom(MapaViewModel::class.java))
  @Suppress("UNCHECKED_CAST")
  return MapaViewModel(ubicacion, lugares, ultimoLugar) as T
 }
}
