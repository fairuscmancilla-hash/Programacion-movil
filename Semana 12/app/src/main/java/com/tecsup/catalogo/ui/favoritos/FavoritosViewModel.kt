package com.tecsup.catalogo.ui.favoritos
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.tecsup.catalogo.data.repository.ItemRepository
import com.tecsup.catalogo.domain.model.Item
import com.tecsup.catalogo.ui.UiState
class FavoritosViewModel(private val repo: ItemRepository) : ViewModel() {
 val estado: StateFlow<UiState<List<Item>>> = repo.favoritos.map<List<Item>,UiState<List<Item>>> { UiState.Exito(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Cargando)
 fun alternarFavorito(id: Int) { viewModelScope.launch { repo.alternarFavorito(id) } }
}
