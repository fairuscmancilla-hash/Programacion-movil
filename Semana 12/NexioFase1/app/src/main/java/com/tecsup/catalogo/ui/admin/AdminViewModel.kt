package com.tecsup.catalogo.ui.admin
import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.tecsup.catalogo.data.repository.ItemRepository
import com.tecsup.catalogo.notification.Notificador
import com.tecsup.catalogo.ui.UiState

data class Totales(val guardados: Int,val favoritos: Int,val mensaje: String="")
class AdminViewModel(private val repo:ItemRepository,private val notificador:Notificador):ViewModel() {
 private val _estado=MutableStateFlow<UiState<Totales>>(UiState.Cargando)
 val estado=_estado.asStateFlow()
 private var ocupado=false
 private var mensaje=""
 init { viewModelScope.launch { repo.items.collect { if(!ocupado)actualizar() } } }
 private suspend fun actualizar(nuevoMensaje:String?=null) {
  if(nuevoMensaje!=null)mensaje=nuevoMensaje
  _estado.value=UiState.Exito(Totales(repo.cantidadGuardada(),repo.cantidadFavoritos(),mensaje))
 }
 fun sincronizar() {
  if(ocupado)return
  ocupado=true
  viewModelScope.launch {
   try {
    _estado.value=UiState.Cargando
    repo.refrescar().fold({n->
     notificador.mostrar("Datos actualizados","Se guardaron $n elementos")
     actualizar("Sincronización completada. Se guardaron $n elementos.")
    },{actualizar("No se pudo sincronizar. Los datos guardados se conservan.")})
   }catch(e:CancellationException){throw e}
    catch(_:Exception){_estado.value=UiState.Error("No se pudieron consultar los totales. Vuelve a intentarlo.")}
    finally{ocupado=false}
  }
 }
 fun limpiarCache() {
  if(ocupado)return
  ocupado=true
  viewModelScope.launch {
   try {_estado.value=UiState.Cargando;repo.limpiarCache();actualizar("Caché eliminada, incluidos los favoritos")}
   catch(e:CancellationException){throw e}
   catch(_:Exception){_estado.value=UiState.Error("No se pudo limpiar la caché. Vuelve a intentarlo.")}
   finally{ocupado=false}
  }
 }
}
