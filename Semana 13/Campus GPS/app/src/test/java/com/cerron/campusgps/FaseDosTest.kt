package com.cerron.campusgps

import com.cerron.campusgps.data.*
import com.cerron.campusgps.model.*
import com.cerron.campusgps.util.filtrarPorCategoria
import com.cerron.campusgps.util.marcarDentroDelRadio
import com.cerron.campusgps.viewmodel.MapaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Test
class UltimoLugarFalso(inicial: Int? = null) : UltimoLugarRepository {
 override val ultimoLugarId = MutableStateFlow(inicial)
 override suspend fun guardar(id: Int) { ultimoLugarId.value = id }
}
private class UbicacionFalsa : UbicacionRepository {
 override fun playServicesDisponible() = true
 override suspend fun obtenerPosicion(): Result<Posicion> = Result.success(Posicion(-12.0464, -77.0428))
}
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class FaseDosTest {
 private val lugares = listOf(
  LugarConDistancia(Lugar(1, "Biblioteca", "Estudio", 0.0, 0.0), 50f),
  LugarConDistancia(Lugar(2, "Cafetería", "Comida", 0.0, 0.0), 150f),
  LugarConDistancia(Lugar(3, "Laboratorio", "Estudio", 0.0, 0.0), null))
 @Test fun filtraSoloLaCategoriaElegida() {
  assertEquals(listOf(1, 3), filtrarPorCategoria(lugares, "Estudio").map { it.lugar.id })
 }
 @Test fun marcaSoloLosLugaresDentroDelRadio() {
  assertEquals(listOf(true, false, false), marcarDentroDelRadio(lugares, 100).map { it.dentroDelRadio })
  assertEquals(listOf(true, true, false), marcarDentroDelRadio(lugares, 200).map { it.dentroDelRadio })
 }
 @Test fun guardaYRecuperaElUltimoLugar() = runTest {
  Dispatchers.setMain(StandardTestDispatcher(testScheduler))
  try {
   val almacen = UltimoLugarFalso(3)
   val vm = MapaViewModel(UbicacionFalsa(), LugaresRepositoryLocal(), almacen)
   advanceUntilIdle()
   assertEquals(3, vm.seleccionado.value)
   vm.seleccionar(2); advanceUntilIdle()
   assertEquals(2, almacen.ultimoLugarId.value)
  } finally { Dispatchers.resetMain() }
 }
 @Test fun sinCategoriaMuestraTodos() {
  assertEquals(3, filtrarPorCategoria(lugares, null).size)
 }
}
