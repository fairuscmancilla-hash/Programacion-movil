package com.cerron.campusgps.data

import kotlinx.coroutines.flow.Flow
interface UltimoLugarRepository {
 val ultimoLugarId: Flow<Int?>
 suspend fun guardar(id: Int)
}
