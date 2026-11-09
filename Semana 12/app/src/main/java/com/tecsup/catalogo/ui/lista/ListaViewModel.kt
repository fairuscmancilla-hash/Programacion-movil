package com.tecsup.catalogo.ui.lista
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.tecsup.catalogo.data.repository.ItemRepository
import com.tecsup.catalogo.domain.model.Item
import com.tecsup.catalogo.notification.Notificador
import com.tecsup.catalogo.ui.UiState
class ListaViewModel(private val repo: ItemRepository, private val notificador: Notificador) : ViewModel() {
 private val _estado = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
 val estado = _estado.asStateFlow()
 private val _aviso = MutableStateFlow<String?>(null)
 val aviso = _aviso.asStateFlow()
 private var terminado = false
 private var trabajo: Job? = null
 init {
  viewModelScope.launch { repo.items.collect { if(it.isNotEmpty() || (terminado && _estado.value !is UiState.Error)) _estado.value = UiState.Exito(it) } }
  refrescar()
 }
 fun refrescar() {
  if(trabajo?.isActive == true) return
  trabajo = viewModelScope.launch {
   if(repo.cantidadGuardada() == 0) _estado.value = UiState.Cargando
   val resultado = repo.refrescar()
   terminado = true
   val datos = repo.items.first()
   resultado.fold({ n -> _estado.value = UiState.Exito(datos); _aviso.value = null; notificador.mostrar("Datos actualizados", "Se guardaron $n elementos") }, { e ->
    if(datos.isEmpty()) _estado.value = UiState.Error(when(e) { is java.io.IOException -> "No hay conexión a internet"; is retrofit2.HttpException -> "Error del servidor: ${e.code()}"; else -> "No se pudieron actualizar los productos" })
    else { _estado.value = UiState.Exito(datos); _aviso.value = when(e) { is java.io.IOException -> "Sin conexión: mostrando datos guardados"; is retrofit2.HttpException -> "El servidor no pudo actualizar los datos (HTTP ${e.code()}). Mostrando datos guardados."; else -> "No se pudo actualizar. Mostrando datos guardados." } }
   })
  }
 }
 fun alternarFavorito(id: Int) { viewModelScope.launch { repo.alternarFavorito(id) } }
}
