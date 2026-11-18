package com.cerron.campusgps
import com.cerron.campusgps.data.*
import com.cerron.campusgps.model.*
import com.cerron.campusgps.util.formatearDistancia
import com.cerron.campusgps.viewmodel.MapaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.Before
import org.junit.After
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class FaseUnoTest {
 @Before fun configurarMain() { Dispatchers.setMain(StandardTestDispatcher()) }
 @After fun restablecerMain() { Dispatchers.resetMain() }
 @Test fun formatoEnLimiteDeKilometro() {
  assertEquals("999 m", formatearDistancia(999f))
  assertEquals("1.0 km", formatearDistancia(1000f))
  assertEquals("1.5 km", formatearDistancia(1500f))
 }
 @Test fun sinServiciosNoSolicitaUbicacion() = runTest {
  val repo = object : UbicacionRepository {
   override fun playServicesDisponible() = false
   override suspend fun obtenerPosicion(): Result<Posicion> = error("No debe llamarse")
  }
  val vm = MapaViewModel(repo, LugaresRepositoryLocal())
  vm.buscar()
  assertEquals(TipoError.SIN_PLAY_SERVICES, (vm.estado.value as UbicacionUi.Error).tipo)
 }
 @Test fun errorGpsYPemisoNoCierranLaApp() = runTest {
  Dispatchers.setMain(StandardTestDispatcher(testScheduler))
  try {
   val repo = object : UbicacionRepository {
    override fun playServicesDisponible() = true
    override suspend fun obtenerPosicion(): Result<Posicion> = Result.failure(GpsNoDisponibleException())
   }
   val vm = MapaViewModel(repo, LugaresRepositoryLocal())
   vm.buscar(); advanceUntilIdle()
   assertEquals(TipoError.GPS_APAGADO, (vm.estado.value as UbicacionUi.Error).tipo)
   vm.permisoDenegado()
   assertEquals(TipoError.SIN_PERMISO, (vm.estado.value as UbicacionUi.Error).tipo)
  } finally { Dispatchers.resetMain() }
 }
}
