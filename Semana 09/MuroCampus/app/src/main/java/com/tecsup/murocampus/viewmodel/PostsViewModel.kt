package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel de MuroCampus. Solo conoce la interface PostRepository:
 * no sabe nada de la red ni de Android; recibe Result y lo convierte en UiState.
 */
class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    // ---------- Paso 6: lista ----------
    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista: StateFlow<UiState<List<Post>>> = _lista.asStateFlow()

    // Trabajos en curso: si llega una nueva peticion se cancela la anterior
    private var trabajoLista: Job? = null

    init {
        cargar()
    }

    /** Paso 6: Cargando -> repo -> Exito / Error. */
    fun cargar() {
        trabajoLista?.cancel()
        trabajoLista = viewModelScope.launch {
            _lista.value = UiState.Cargando
            repo.listar()
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }
}
