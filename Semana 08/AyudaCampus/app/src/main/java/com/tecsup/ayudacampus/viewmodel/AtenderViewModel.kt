package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.data.local.entity.prioridadEnum
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AtenderViewModel(
    private val repo: AyudaRepository,
    private val usuario: Usuario,
    private val ticketId: Int
) : ViewModel() {

    companion object {
        /** Estados que Soporte puede elegir (CERRADO solo lo hace el estudiante) */
        val ESTADOS_SOPORTE = listOf(EstadoTicket.ABIERTO, EstadoTicket.EN_PROCESO, EstadoTicket.RESUELTO)
    }

    val ticket: StateFlow<TicketConDetalle?> = repo.observarTicket(ticketId)
        .map { d -> d?.copy(comentarios = d.comentarios.sortedBy { it.fecha }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Cambios pendientes: se aplican con guardar()
    private val _estado = MutableStateFlow<EstadoTicket?>(null)
    val estado: StateFlow<EstadoTicket?> = _estado.asStateFlow()

    private val _prioridad = MutableStateFlow<Prioridad?>(null)
    val prioridad: StateFlow<Prioridad?> = _prioridad.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    init {
        viewModelScope.launch {
            val t = repo.observarTicket(ticketId).filterNotNull().first().ticket
            _estado.value = t.estadoEnum
            _prioridad.value = t.prioridadEnum
        }
    }

    private val cerrado: Boolean get() = ticket.value?.ticket?.estadoEnum == EstadoTicket.CERRADO

    fun cambiarEstado(e: EstadoTicket) {
        when {
            cerrado -> _mensaje.value = "El ticket está cerrado: ya no se puede cambiar"
            e !in ESTADOS_SOPORTE -> _mensaje.value = "Solo el estudiante puede cerrar el ticket"
            else -> _estado.value = e
        }
    }

    fun cambiarPrioridad(p: Prioridad) {
        if (cerrado) {
            _mensaje.value = "El ticket está cerrado: ya no se puede cambiar"
            return
        }
        _prioridad.value = p
    }

    fun responder(texto: String) {
        if (texto.isBlank() || cerrado || ticket.value == null) return
        viewModelScope.launch { repo.agregarComentario(ticketId, usuario, texto.trim()) }
    }

    fun guardar() {
        val actual = ticket.value?.ticket ?: return
        if (cerrado) {
            _mensaje.value = "El ticket está cerrado: ya no se puede cambiar"
            return
        }
        viewModelScope.launch {
            _estado.value?.let {
                if (it != actual.estadoEnum && it in ESTADOS_SOPORTE) {
                    repo.cambiarEstado(ticketId, it)
                    // Queda en la conversación y le llega al estudiante como aviso
                    repo.agregarComentario(ticketId, usuario, "Cambió el estado a ${it.etiqueta}")
                }
            }
            _prioridad.value?.let { if (it != actual.prioridadEnum) repo.cambiarPrioridad(ticketId, it) }
            _mensaje.value = "Cambios guardados"
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}
