package com.tecsup.catalogo.ui.detalle
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.tecsup.catalogo.data.repository.ItemRepository
import com.tecsup.catalogo.domain.model.Item
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.ui.mensajeDeError
class DetalleViewModel(private val repo: ItemRepository, private val id: Int) : ViewModel() {
 private val _estado = MutableStateFlow<UiState<Item>>(UiState.Cargando)
 val estado = _estado.asStateFlow()
 init { cargar(); viewModelScope.launch { repo.items.collect { lista -> lista.find { it.id == id }?.let { if(_estado.value is UiState.Exito) _estado.value = UiState.Exito(it) } } } }
 fun cargar() { viewModelScope.launch { _estado.value = UiState.Cargando; _estado.value = repo.obtenerDetalle(id).fold({UiState.Exito(it)}, {UiState.Error(mensajeDeError(it))}) } }
 fun alternarFavorito() { viewModelScope.launch { repo.alternarFavorito(id) } }
}
