package com.cerron.centrotareas.data

import com.cerron.centrotareas.model.AvancePrimos
import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    fun tareaConProgreso(): Flow<Float>
    fun contarPrimosConAvance(hasta: Int): Flow<AvancePrimos>
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>
}
