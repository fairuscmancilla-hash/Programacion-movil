package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.data.local.entity.prioridadEnum
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoSla
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
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

    /** Categorías para los chips del filtro (también las inactivas: pueden tener tickets) */
    val categorias: StateFlow<List<Categoria>> = repo.observarCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ---------- Filtros (null = todos) ----------
    private val _filtro = MutableStateFlow(EstadoTicket.entries.firstOrNull { it.name == filtroInicial })
    val filtro: StateFlow<EstadoTicket?> = _filtro.asStateFlow()

    private val _prioridad = MutableStateFlow<Prioridad?>(null)
    val prioridad: StateFlow<Prioridad?> = _prioridad.asStateFlow()

    private val _categoriaId = MutableStateFlow<Int?>(null)
    val categoriaId: StateFlow<Int?> = _categoriaId.asStateFlow()

    private val _texto = MutableStateFlow("")
    val texto: StateFlow<String> = _texto.asStateFlow()

    /**
     * Aplica estado + prioridad + categoría + texto a la vez.
     * Filtra sin reordenar: el DAO ya entrega ALTA → MEDIA → BAJA y el más reciente primero.
     */
    val tickets: StateFlow<List<TicketConDetalle>> =
        combine(todos, _filtro, _prioridad, _categoriaId, _texto) { lista, estado, prioridad, categoria, texto ->
            lista.filter { d ->
                (estado == null || d.ticket.estadoEnum == estado) &&
                    (prioridad == null || d.ticket.prioridadEnum == prioridad) &&
                    (categoria == null || d.ticket.categoriaId == categoria) &&
                    coincide(d, texto)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val hayFiltros: StateFlow<Boolean> =
        combine(_filtro, _prioridad, _categoriaId, _texto) { e, p, c, t -> e != null || p != null || c != null || t.isNotBlank() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun filtrar(estado: EstadoTicket?) {
        _filtro.value = estado
    }

    fun filtrarPrioridad(p: Prioridad?) {
        _prioridad.value = p
    }

    fun filtrarCategoria(id: Int?) {
        _categoriaId.value = id
    }

    fun buscar(texto: String) {
        _texto.value = texto
    }

    fun limpiarFiltros() {
        _filtro.value = null
        _prioridad.value = null
        _categoriaId.value = null
        _texto.value = ""
    }

    /** Título, nombre del cliente o número ("12", "#12" o "#0012"). */
    private fun coincide(d: TicketConDetalle, texto: String): Boolean {
        val t = texto.trim()
        if (t.isEmpty()) return true
        if (d.ticket.titulo.contains(t, ignoreCase = true)) return true
        if (d.cliente?.nombre?.contains(t, ignoreCase = true) == true) return true
        val numero = t.removePrefix("#").trimStart('0')
        return numero.isNotEmpty() && numero.all { it.isDigit() } && d.ticket.id.toString() == numero
    }
}
