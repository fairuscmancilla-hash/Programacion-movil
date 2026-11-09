package com.tecsup.catalogo.ui.lista
import com.tecsup.catalogo.domain.model.Item
import com.tecsup.catalogo.ui.UiState
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

/** R12: pruebas de ListaViewModel con un repositorio y un notificador falsos. */
@OptIn(ExperimentalCoroutinesApi::class)
class ListaViewModelTest {
 // viewModelScope usa Dispatchers.Main; en las pruebas lo cambiamos por uno controlado.
 private val dispatcher = StandardTestDispatcher()
 private val notificador = FakeNotificador()

 @Before fun setUp() { Dispatchers.setMain(dispatcher) }
 @After fun tearDown() { Dispatchers.resetMain() }

 private fun item(id: Int, favorito: Boolean = false) =
  Item(id, "Celular $id", "Descripción", 100.0, "https://example.com/$id.png", "Marca", 4.5, 10, favorito)

 @Test fun alAbrirMuestraCargandoHastaQueTermineElPrimerRefresco() = runTest(dispatcher) {
  val repo = FakeItemRepository().apply { respuestaApi = Result.success(listOf(item(1))) }
  val vm = ListaViewModel(repo, notificador)
  assertEquals(UiState.Cargando, vm.estado.value)
  advanceUntilIdle()
  assertTrue(vm.estado.value is UiState.Exito)
 }

 @Test fun conInternetMuestraLaListaYNotificaCuantosSeGuardaron() = runTest(dispatcher) {
  val repo = FakeItemRepository().apply { respuestaApi = Result.success(listOf(item(1), item(2), item(3))) }
  val vm = ListaViewModel(repo, notificador)
  advanceUntilIdle()
  assertEquals(UiState.Exito(listOf(item(1), item(2), item(3))), vm.estado.value)
  assertNull(vm.aviso.value)
  assertEquals(listOf("Datos actualizados" to "Se guardaron 3 elementos"), notificador.mostrados)
 }

 @Test fun sinInternetYSinDatosGuardadosMuestraError() = runTest(dispatcher) {
  val repo = FakeItemRepository().apply { respuestaApi = Result.failure(IOException()) }
  val vm = ListaViewModel(repo, notificador)
  advanceUntilIdle()
  assertEquals(UiState.Error("No hay conexión a internet"), vm.estado.value)
  assertTrue(notificador.mostrados.isEmpty())
 }

 @Test fun sinInternetConDatosGuardadosMuestraLosDatosYElAviso() = runTest(dispatcher) {
  val guardados = listOf(item(1), item(2))
  val repo = FakeItemRepository(guardados).apply { respuestaApi = Result.failure(IOException()) }
  val vm = ListaViewModel(repo, notificador)
  advanceUntilIdle()
  assertEquals(UiState.Exito(guardados), vm.estado.value)
  assertEquals("Sin conexión: mostrando datos guardados", vm.aviso.value)
  assertTrue(notificador.mostrados.isEmpty())
 }

 @Test fun errorDelServidorSinDatosMuestraElCodigo() = runTest(dispatcher) {
  val error = HttpException(Response.error<Any>(500, "".toResponseBody()))
  val repo = FakeItemRepository().apply { respuestaApi = Result.failure(error) }
  val vm = ListaViewModel(repo, notificador)
  advanceUntilIdle()
  assertEquals(UiState.Error("El servidor respondió con error 500"), vm.estado.value)
 }

 @Test fun reintentarDespuesDeUnErrorCargaLaLista() = runTest(dispatcher) {
  val repo = FakeItemRepository().apply { respuestaApi = Result.failure(IOException()) }
  val vm = ListaViewModel(repo, notificador)
  advanceUntilIdle()
  assertTrue(vm.estado.value is UiState.Error)
  repo.respuestaApi = Result.success(listOf(item(7)))
  vm.refrescar()
  advanceUntilIdle()
  assertEquals(UiState.Exito(listOf(item(7))), vm.estado.value)
  assertEquals(2, repo.vecesRefrescado)
 }

 @Test fun alternarFavoritoActualizaLaListaDesdeElRepositorio() = runTest(dispatcher) {
  val repo = FakeItemRepository().apply { respuestaApi = Result.success(listOf(item(1), item(2))) }
  val vm = ListaViewModel(repo, notificador)
  advanceUntilIdle()
  vm.alternarFavorito(2)
  advanceUntilIdle()
  assertEquals(listOf(2), repo.favoritosAlternados)
  assertEquals(UiState.Exito(listOf(item(1), item(2, favorito = true))), vm.estado.value)
 }
}
