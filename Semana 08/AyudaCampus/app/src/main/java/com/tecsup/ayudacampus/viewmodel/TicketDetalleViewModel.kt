package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TicketDetalleViewModel(
    private val repo: AyudaRepository,
    private val usuario: Usuario,
    private val ticketId: Int
) : ViewModel() {

    /** Flow de Room: si Soporte responde o cambia el estado, se actualiza solo */
    val ticket: StateFlow<TicketConDetalle?> = repo.observarTicket(ticketId)
        .map { d -> d?.copy(comentarios = d.comentarios.sortedBy { it.fecha }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val estado: EstadoTicket? get() = ticket.value?.ticket?.estadoEnum

    fun comentar(texto: String) {
        if (texto.isBlank() || estado == null || estado == EstadoTicket.CERRADO) return
        viewModelScope.launch { repo.agregarComentario(ticketId, usuario, texto.trim()) }
    }

    /** Solo si está RESUELTO y con calificación de 1 a 5 (obligatoria). */
    fun cerrar(calificacion: Int, comentarioFinal: String = "") {
        if (estado != EstadoTicket.RESUELTO || calificacion !in 1..5) return
        viewModelScope.launch {
            if (comentarioFinal.isNotBlank()) repo.agregarComentario(ticketId, usuario, comentarioFinal.trim())
            repo.cerrarConCalificacion(ticketId, calificacion)
        }
    }

    /** Solo si está RESUELTO: vuelve a ABIERTO y se borra la calificación. */
    fun reabrir() {
        if (estado != EstadoTicket.RESUELTO) return
        viewModelScope.launch { repo.reabrir(ticketId) }
    }
}
