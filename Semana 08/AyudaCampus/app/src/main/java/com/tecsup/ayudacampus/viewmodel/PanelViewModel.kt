package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.graficos.DatoGrafico
import com.tecsup.ayudacampus.ui.theme.color
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PanelViewModel(private val repo: AyudaRepository) : ViewModel() {

    private fun contar(estado: EstadoTicket) = repo.observarCantidadPorEstado(estado)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val abiertos: StateFlow<Int> = contar(EstadoTicket.ABIERTO)
    val enProceso: StateFlow<Int> = contar(EstadoTicket.EN_PROCESO)
    val resueltos: StateFlow<Int> = contar(EstadoTicket.RESUELTO)
    private val cerrados: StateFlow<Int> = contar(EstadoTicket.CERRADO)

    /** Conteos de Room -> datos del gráfico de dona */
    val porEstado: StateFlow<List<DatoGrafico>> =
        combine(abiertos, enProceso, resueltos, cerrados) { a, p, r, c ->
            listOf(
                DatoGrafico(EstadoTicket.ABIERTO.etiqueta, a.toFloat(), EstadoTicket.ABIERTO.color()),
                DatoGrafico(EstadoTicket.EN_PROCESO.etiqueta, p.toFloat(), EstadoTicket.EN_PROCESO.color()),
                DatoGrafico(EstadoTicket.RESUELTO.etiqueta, r.toFloat(), EstadoTicket.RESUELTO.color()),
                DatoGrafico(EstadoTicket.CERRADO.etiqueta, c.toFloat(), EstadoTicket.CERRADO.color())
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Conteos del LEFT JOIN -> datos del gráfico de barras */
    val porCategoria: StateFlow<List<DatoGrafico>> = repo.observarCantidadPorCategoria()
        .map { lista -> lista.map { DatoGrafico(it.nombre.substringBefore(" /"), it.cantidad.toFloat()) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** límite = ahora − 1 hora, recalculado cada minuto para que los tickets nuevos entren al cumplir la hora */
    private val limite = flow {
        while (true) {
            emit(System.currentTimeMillis() - 60 * 60 * 1000L)
            delay(60_000)
        }
    }

    /** ALTA + ABIERTO con más de 1 hora de antigüedad */
    @OptIn(ExperimentalCoroutinesApi::class)
    val altasSinAtender: StateFlow<Int> = limite
        .flatMapLatest { repo.observarAltasSinAtender(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    /** Tiempo promedio de resolución en milisegundos (null = aún no hay tickets cerrados) */
    val promedioResolucion: StateFlow<Long?> = repo.observarPromedioResolucion()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
