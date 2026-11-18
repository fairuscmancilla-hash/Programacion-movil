package com.cerron.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cerron.campusgps.data.LugaresRepository
import com.cerron.campusgps.data.UbicacionRepository
class CampusViewModelFactory(private val ubicacion: UbicacionRepository, private val lugares: LugaresRepository) : ViewModelProvider.Factory {
 override fun <T : ViewModel> create(modelClass: Class<T>): T {
  require(modelClass.isAssignableFrom(MapaViewModel::class.java))
  @Suppress("UNCHECKED_CAST")
  return MapaViewModel(ubicacion, lugares) as T
 }
}
