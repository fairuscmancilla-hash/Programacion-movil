package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ResumenTickets(val abiertos: Int = 0, val resueltos: Int = 0, val total: Int = 0)

class MisTicketsViewModel(repo: AyudaRepository, usuario: Usuario) : ViewModel() {

    private val todos = repo.observarTicketsCliente(usuario.id)

    /** null = Todos (incluye CERRADOS) */
    private val _filtro = MutableStateFlow<EstadoTicket?>(null)
    val filtro: StateFlow<EstadoTicket?> = _filtro.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val tickets: StateFlow<List<TicketConDetalle>> =
        combine(todos, _filtro, _busqueda) { lista, estado, texto ->
            lista.filter { d ->
                (estado == null || d.ticket.estadoEnum == estado) && coincide(d, texto)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** ticketId -> tiempo restante del SLA; se recalcula cada minuto */
    val sla: StateFlow<Map<Int, EstadoSla>> =
        combine(todos, CalculoSla.reloj()) { lista, ahora -> CalculoSla.mapa(lista, ahora) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val resumen: StateFlow<ResumenTickets> = todos.map { lista ->
        ResumenTickets(
            abiertos = lista.count { it.ticket.estadoEnum in listOf(EstadoTicket.ABIERTO, EstadoTicket.EN_PROCESO) },
            resueltos = lista.count { it.ticket.estadoEnum in listOf(EstadoTicket.RESUELTO, EstadoTicket.CERRADO) },
            total = lista.size
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ResumenTickets())

    fun filtrar(estado: EstadoTicket?) {
        _filtro.value = estado
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    /** Busca por título o por número ("12", "#12" o "#0012"). */
    private fun coincide(d: TicketConDetalle, texto: String): Boolean {
        val t = texto.trim()
        if (t.isEmpty()) return true
        if (d.ticket.titulo.contains(t, ignoreCase = true)) return true
        val numero = t.removePrefix("#").trimStart('0')
        return numero.isNotEmpty() && numero.all { it.isDigit() } && d.ticket.id.toString() == numero
    }
}
