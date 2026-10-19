package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriasViewModel(private val repo: AyudaRepository) : ViewModel() {

    val categorias: StateFlow<List<Categoria>> = repo.observarCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** categoriaId -> cantidad de tickets (para la vista previa y para no eliminar con tickets) */
    val ticketsPorCategoria: StateFlow<Map<Int, Int>> = repo.observarCantidadPorCategoria()
        .map { lista -> lista.associate { it.categoriaId to it.cantidad } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyMap())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun alternar(c: Categoria) {
        viewModelScope.launch { repo.cambiarActiva(c.id, !c.activa) }
    }

    /** Nueva (id = 0) o editada. Devuelve false si el nombre está vacío. */
    fun guardar(categoria: Categoria): Boolean {
        if (categoria.nombre.isBlank()) {
            _mensaje.value = "El nombre es obligatorio"
            return false
        }
        viewModelScope.launch { repo.guardarCategoria(categoria.copy(nombre = categoria.nombre.trim())) }
        return true
    }

    /** No se puede eliminar una categoría que ya tiene tickets. Devuelve true si se elimina. */
    fun eliminar(categoria: Categoria): Boolean {
        val cantidad = ticketsPorCategoria.value[categoria.id] ?: 0
        if (cantidad > 0) {
            _mensaje.value = "No se puede eliminar \"${categoria.nombre}\": tiene $cantidad ticket(s). Desactívala."
            return false
        }
        viewModelScope.launch { repo.eliminarCategoria(categoria) }
        return true
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}
