package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Aviso
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Avisos de la campana, calculados desde Room:
 * - Soporte: tickets nuevos y comentarios de los estudiantes.
 * - Estudiante: respuestas y cambios de estado que hace Soporte en sus tickets.
 */
class AvisosViewModel(
    repo: AyudaRepository,
    private val prefs: PreferenciasStore,
    usuario: Usuario
) : ViewModel() {

    private val rol = usuario.rol
    private val esSoporte = rol == Rol.ADMIN.name

    private val eventos: Flow<List<Aviso>> =
        (if (esSoporte) repo.observarTodosLosTickets() else repo.observarTicketsCliente(usuario.id))
            .map { lista -> lista.flatMap(::avisosDe).sortedByDescending { it.fecha }.take(30) }

    /** Switch "Notificaciones" de DataStore */
    val activadas: StateFlow<Boolean> =
        prefs.notificaciones.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val avisos: StateFlow<List<Aviso>> =
        combine(eventos, prefs.avisosVistos(rol)) { lista, visto ->
            lista.map { it.copy(leido = it.fecha <= visto) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val noLeidos: StateFlow<Int> =
        combine(avisos, activadas) { lista, on -> if (on) lista.count { !it.leido } else 0 }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    /** Avisos que todavía no salieron como notificación del sistema (máximo 3 a la vez) */
    val porNotificar: StateFlow<List<Aviso>> =
        combine(eventos, prefs.avisosNotificados(rol), activadas) { lista, ultima, on ->
            if (!on || ultima == 0L) emptyList() else lista.filter { it.fecha > ultima }.take(3)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Primera vez en este perfil: no se notifica todo el historial, solo lo que llegue desde ahora
        viewModelScope.launch {
            if (prefs.avisosNotificados(rol).first() == 0L) {
                prefs.guardarAvisosNotificados(rol, System.currentTimeMillis())
            }
        }
    }

    fun marcarLeidos() {
        viewModelScope.launch { prefs.guardarAvisosVistos(rol, System.currentTimeMillis()) }
    }

    fun marcarNotificados(lista: List<Aviso>) {
        val ultima = lista.maxOfOrNull { it.fecha } ?: return
        viewModelScope.launch { prefs.guardarAvisosNotificados(rol, ultima) }
    }

    private fun avisosDe(d: TicketConDetalle): List<Aviso> {
        val numero = "#%04d".format(d.ticket.id)
        return if (esSoporte) {
            val nuevo = Aviso(
                d.ticket.id, "Nuevo ticket $numero",
                "${d.ticket.titulo} · ${d.cliente?.nombre ?: "Estudiante"}", d.ticket.fecha
            )
            val comentarios = d.comentarios.filter { it.autorRol == Rol.CLIENTE.name }.map {
                Aviso(d.ticket.id, "${it.autorNombre} comentó en $numero", it.texto, it.fecha)
            }
            listOf(nuevo) + comentarios
        } else {
            d.comentarios.filter { it.autorRol == Rol.ADMIN.name }.map {
                Aviso(d.ticket.id, "Soporte TI en $numero · ${d.ticket.titulo}", it.texto, it.fecha)
            }
        }
    }
}
