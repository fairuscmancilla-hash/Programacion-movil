package com.tecsup.catalogo

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelStore
import androidx.test.platform.app.InstrumentationRegistry
import com.tecsup.catalogo.ui.admin.AdminViewModel
import com.tecsup.catalogo.ui.admin.Totales
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.notification.Notificador
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tecsup.catalogo.data.local.AppDatabase
import com.tecsup.catalogo.data.mapper.aEntity
import com.tecsup.catalogo.data.remote.ApiService
import com.tecsup.catalogo.data.remote.dto.ItemDto
import com.tecsup.catalogo.data.remote.dto.ProductosDto
import com.tecsup.catalogo.data.repository.ItemRepositoryImpl
import com.tecsup.catalogo.domain.model.Moneda
import com.tecsup.catalogo.notification.NotificadorAndroid
import java.io.IOException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Response

/** Pruebas de integración con Room en memoria; no borran los datos del usuario. */
@RunWith(AndroidJUnit4::class)
class IntegracionFase1Test {
 private lateinit var db: AppDatabase
 private lateinit var api: ApiControlada
 private lateinit var repo: ItemRepositoryImpl
 private val producto=ItemDto(123,"iPhone 13 Pro","English description",1099.99,"https://example.com/imagen.png","Apple",4.12,56)
 @Before fun preparar() {
  db=Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),AppDatabase::class.java).build()
  api=ApiControlada(producto)
  repo=ItemRepositoryImpl(api,db)
 }
 @After fun cerrar()=db.close()

 @Test fun sincronizarConservaFavoritos()= runBlocking {
  assertEquals(1,repo.refrescar().getOrThrow())
  repo.alternarFavorito(123)
  api.producto=producto.copy(price=1200.0)
  repo.refrescar().getOrThrow()
  assertTrue(repo.items.first().single().esFavorito)
  assertEquals(1200.0,repo.items.first().single().precio,0.0)
 }
 @Test fun favoritoCambiadoDuranteDescargaSeConserva()=runBlocking {
  repo.refrescar().getOrThrow()
  api.antesDeResponder={repo.alternarFavorito(123)}
  repo.refrescar().getOrThrow()
  assertTrue(repo.items.first().single().esFavorito)
 }
 @Test fun sinInternetNoBorraLaCache()=runBlocking {
  repo.refrescar().getOrThrow()
  api.fallo=IOException("Sin internet")
  assertTrue(repo.refrescar().isFailure)
  assertEquals(1,repo.cantidadGuardada())
  assertEquals("iPhone 13 Pro",repo.items.first().single().titulo)
 }
 @Test fun errorHttpNoBorraLaCache()=runBlocking {
  repo.refrescar().getOrThrow()
  api.fallo=HttpException(Response.error<ProductosDto>(503,"sin servicio".toResponseBody()))
  assertTrue(repo.refrescar().isFailure)
  assertEquals(1,repo.cantidadGuardada())
 }
 @Test fun sinInternetNiCacheDevuelveFallo()=runBlocking {
  api.fallo=IOException("Sin internet")
  assertTrue(repo.refrescar().isFailure)
  assertTrue(repo.items.first().isEmpty())
 }
 @Test fun apiVaciaConBaseNuevaPermiteEstadoVacio()=runBlocking {
  api.vacio=true
  assertEquals(0,repo.refrescar().getOrThrow())
  assertTrue(repo.items.first().isEmpty())
 }
 @Test fun detalleConsultaSegundoEndpointYGuardaEnRoom()=runBlocking {
  val item=repo.obtenerDetalle(123).getOrThrow()
  assertEquals(123,api.ultimoDetalle)
  assertEquals(123,item.id)
  assertEquals(1,repo.cantidadGuardada())
 }
 @Test fun detalleUsaCacheSinInternet()=runBlocking {
  repo.refrescar().getOrThrow()
  api.fallo=IOException("Sin internet")
  assertEquals(123,repo.obtenerDetalle(123).getOrThrow().id)
 }
 @Test fun limpiarCacheDejaTotalesEnCeroYPermiteRecuperar()=runBlocking {
  repo.refrescar().getOrThrow();repo.alternarFavorito(123)
  repo.limpiarCache()
  assertEquals(0,repo.cantidadGuardada());assertEquals(0,repo.cantidadFavoritos())
  assertTrue(repo.items.first().isEmpty())
  repo.refrescar().getOrThrow()
  assertEquals(1,repo.cantidadGuardada());assertEquals(0,repo.cantidadFavoritos())
 }
 @Test fun traduccionYConversionFuncionanDesdeRoomSinRed()=runBlocking {
  db.itemDao().guardarTodos(listOf(producto.aEntity(true)))
  api.fallo=IOException("Sin internet")
  val item=repo.items.first().single()
  assertTrue(item.descripcion.startsWith("El iPhone 13 Pro"))
  assertEquals("S/ 3,794.97",Moneda.formatearSoles(item.precio))
  assertEquals(1099.99,item.precio,0.0)
  assertTrue(item.esFavorito)
 }
 @Test fun sincronizarNoConvierteDosVecesElPrecio()=runBlocking {
  repeat(3){repo.refrescar().getOrThrow()}
  assertEquals("S/ 3,794.97",Moneda.formatearSoles(repo.items.first().single().precio))
 }
 @Test fun avisoAntesDePermisoSeEntregaAlConcederloUnaSolaVez() {
  val contexto=PermisoControlado(ApplicationProvider.getApplicationContext())
  val manager=contexto.getSystemService(NotificationManager::class.java)
  manager.cancel(11)
  val notificador=NotificadorAndroid(contexto)
  notificador.mostrar("Datos actualizados","Se guardaron 3 elementos")
  assertFalse(manager.activeNotifications.any{it.id==11})
  contexto.concedido=true
  notificador.actualizarPermiso(true)
  esperar { manager.activeNotifications.any{it.id==11} }
  val aviso=manager.activeNotifications.single{it.id==11}
  assertEquals("Se guardaron 3 elementos",aviso.notification.extras.getString(Notification.EXTRA_TEXT))
  manager.cancel(11)
  // Android publica y quita notificaciones de forma asíncrona: se espera antes de comprobar.
  esperar { manager.activeNotifications.none{it.id==11} }
  notificador.actualizarPermiso(true)
  Thread.sleep(300)
  assertFalse(manager.activeNotifications.any{it.id==11})
 }
 // Espera hasta 2 s a que el sistema refleje el cambio.
 private fun esperar(condicion: () -> Boolean) {
  val limite=System.currentTimeMillis()+2000
  while(!condicion() && System.currentTimeMillis()<limite) Thread.sleep(50)
 }
 @Test fun negarPermisoDescartaAvisoPendiente() {
  val contexto=PermisoControlado(ApplicationProvider.getApplicationContext())
  val manager=contexto.getSystemService(NotificationManager::class.java);manager.cancel(11)
  val notificador=NotificadorAndroid(contexto)
  notificador.mostrar("Datos actualizados","Se guardaron 3 elementos")
  notificador.actualizarPermiso(false)
  contexto.concedido=true;notificador.actualizarPermiso(true)
  assertFalse(manager.activeNotifications.any{it.id==11})
 }

 @Test fun panelConservaConfirmacionCuandoRoomEmite()=runBlocking {
  repo.refrescar().getOrThrow()
  val instrumentation=InstrumentationRegistry.getInstrumentation()
  val store=ViewModelStore()
  lateinit var vm:AdminViewModel
  instrumentation.runOnMainSync {
   vm=AdminViewModel(repo,object:Notificador{override fun mostrar(titulo:String,texto:String){}})
   store.put("admin",vm)
  }
  fun datos()=(vm.estado.value as? UiState.Exito<Totales>)?.datos
  fun esperar(condicion:()->Boolean) {
   val fin=android.os.SystemClock.elapsedRealtime()+5000
   while(!condicion() && android.os.SystemClock.elapsedRealtime()<fin)android.os.SystemClock.sleep(30)
   assertTrue(condicion())
  }
  try {
   esperar{datos()!=null}
   instrumentation.runOnMainSync{vm.sincronizar()}
   esperar{datos()?.mensaje?.contains("Sincronización completada")==true}
   repo.alternarFavorito(123)
   esperar{datos()?.favoritos==1}
   assertTrue(datos()!!.mensaje.contains("Sincronización completada"))
  }finally{instrumentation.runOnMainSync{store.clear()}}
 }


 @Test fun preferenciaDeIngresoPersisteParaReaperturaSinSesion() {
  val contexto=InstrumentationRegistry.getInstrumentation().context
  contexto.getSharedPreferences("nexio_inicio",Context.MODE_PRIVATE).edit().clear().commit()
  val preferencias=com.tecsup.catalogo.data.local.PreferenciasInicio(contexto)
  assertFalse(preferencias.ingresoHabilitado())
  preferencias.habilitarIngreso()
  assertTrue(com.tecsup.catalogo.data.local.PreferenciasInicio(contexto).ingresoHabilitado())
 }

 private class PermisoControlado(base:Context):ContextWrapper(base) {
  var concedido=false
  override fun checkPermission(permission:String,pid:Int,uid:Int):Int = if(permission==Manifest.permission.POST_NOTIFICATIONS) {if(concedido)PackageManager.PERMISSION_GRANTED else PackageManager.PERMISSION_DENIED} else super.checkPermission(permission,pid,uid)
  override fun checkSelfPermission(permission:String):Int = if(permission==Manifest.permission.POST_NOTIFICATIONS) {if(concedido)PackageManager.PERMISSION_GRANTED else PackageManager.PERMISSION_DENIED} else super.checkSelfPermission(permission)
 }
 private class ApiControlada(var producto:ItemDto):ApiService {
  var fallo:Exception?=null
  var vacio=false
  var ultimoDetalle:Int?=null
  var antesDeResponder:(suspend ()->Unit)?=null
  override suspend fun listar():ProductosDto {fallo?.let{throw it};antesDeResponder?.invoke();return ProductosDto(if(vacio)emptyList()else listOf(producto))}
  override suspend fun detalle(id:Int):ItemDto {ultimoDetalle=id;fallo?.let{throw it};return producto}
 }
}
