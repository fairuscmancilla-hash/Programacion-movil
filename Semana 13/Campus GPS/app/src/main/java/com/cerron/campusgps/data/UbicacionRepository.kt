package com.cerron.campusgps.data

import com.cerron.campusgps.model.Posicion
interface UbicacionRepository {
 suspend fun obtenerPosicion(): Result<Posicion>
}
