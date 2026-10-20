package com.tecsup.ayudacampus.viewmodel

import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.estadoEnum
import com.tecsup.ayudacampus.data.local.entity.prioridadEnum
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.model.EstadoSla
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** Regla del SLA (la usan MisTicketsViewModel y BandejaViewModel). */
object CalculoSla {

    /** Solo corre mientras el ticket está ABIERTO o EN_PROCESO; resuelto o cerrado ya no tiene SLA. */
    fun de(t: Ticket, ahora: Long): EstadoSla? {
        if (t.estadoEnum != EstadoTicket.ABIERTO && t.estadoEnum != EstadoTicket.EN_PROCESO) return null
        val horas = t.prioridadEnum.horasRespuesta
        return EstadoSla(restanteMs = t.fecha + horas * 3_600_000L - ahora, limiteHoras = horas)
    }

    fun mapa(lista: List<TicketConDetalle>, ahora: Long): Map<Int, EstadoSla> =
        lista.mapNotNull { d -> de(d.ticket, ahora)?.let { d.ticket.id to it } }.toMap()

    /** Emite la hora actual cada minuto para que el contador avance solo. */
    fun reloj(): Flow<Long> = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(60_000)
        }
    }
}
