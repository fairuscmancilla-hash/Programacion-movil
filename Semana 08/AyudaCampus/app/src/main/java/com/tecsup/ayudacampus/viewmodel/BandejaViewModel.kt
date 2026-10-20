package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoSla
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BandejaViewModel(repo: AyudaRepository, filtroInicial: String = "") : ViewModel() {

    private val todos = repo.observarTodosLosTickets()

    /** ticketId -> tiempo restante del SLA; se recalcula cada minuto */
    val sla: StateFlow<Map<Int, EstadoSla>> =
        combine(todos, CalculoSla.reloj()) { lista, ahora -> CalculoSla.mapa(lista, ahora) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val _filtro = MutableStateFlow(EstadoTicket.entries.firstOrNull { it.name == filtroInicial })
    val filtro: StateFlow<EstadoTicket?> = _filtro.asStateFlow()

    /** Filtra sin reordenar: el DAO ya entrega ALTA → MEDIA → BAJA y el más reciente primero */
    val tickets: StateFlow<List<TicketConDetalle>> =
        combine(todos, _filtro) { lista, estado ->
            if (estado == null) lista else lista.filter { it.ticket.estadoEnum == estado }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun filtrar(estado: EstadoTicket?) {
        _filtro.value = estado
    }
}
