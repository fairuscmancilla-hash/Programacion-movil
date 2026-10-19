package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FormularioTicket(
    val categoriaId: Int? = null,
    val titulo: String = "",
    val ubicacion: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val descripcion: String = "",
    val errorCategoria: String? = null,
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val enviando: Boolean = false
)

class NuevoTicketViewModel(
    private val repo: AyudaRepository,
    prefs: PreferenciasStore,
    private val usuario: Usuario
) : ViewModel() {

    /** Solo categorías activas */
    val categorias: StateFlow<List<Categoria>> = repo.observarCategoriasActivas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Si Soporte apagó "Recibir tickets", la mesa de ayuda está cerrada */
    val mesaAbierta: StateFlow<Boolean> =
        prefs.recibirTickets.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    private val _form = MutableStateFlow(FormularioTicket())
    val form: StateFlow<FormularioTicket> = _form.asStateFlow()

    fun elegirCategoria(id: Int) = _form.update { it.copy(categoriaId = id, errorCategoria = null) }
    fun cambiarTitulo(v: String) = _form.update { it.copy(titulo = v, errorTitulo = null) }
    fun cambiarUbicacion(v: String) = _form.update { it.copy(ubicacion = v) }
    fun cambiarPrioridad(p: Prioridad) = _form.update { it.copy(prioridad = p) }
    fun cambiarDescripcion(v: String) = _form.update { it.copy(descripcion = v, errorDescripcion = null) }

    fun enviar(onCreado: () -> Unit) {
        val f = _form.value
        if (f.enviando || !mesaAbierta.value) return
        val activa = categorias.value.any { it.id == f.categoriaId }
        val conErrores = f.copy(
            errorCategoria = if (!activa) "Elige una categoría" else null,
            errorTitulo = if (f.titulo.trim().length < 5) "El título debe tener al menos 5 caracteres" else null,
            errorDescripcion = if (f.descripcion.trim().length < 10) "La descripción debe tener al menos 10 caracteres" else null
        )
        if (conErrores.errorCategoria != null || conErrores.errorTitulo != null || conErrores.errorDescripcion != null) {
            _form.value = conErrores
            return
        }
        _form.value = f.copy(enviando = true)
        viewModelScope.launch {
            repo.crearTicket(
                clienteId = usuario.id,
                categoriaId = f.categoriaId!!,
                titulo = f.titulo.trim(),
                descripcion = f.descripcion.trim(),
                ubicacion = f.ubicacion.trim(),
                prioridad = f.prioridad
            )
            _form.value = FormularioTicket()
            onCreado()
        }
    }
}
