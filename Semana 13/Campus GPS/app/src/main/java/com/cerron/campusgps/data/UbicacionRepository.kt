package com.cerron.campusgps.data

import com.cerron.campusgps.model.Posicion
interface UbicacionRepository {
 suspend fun obtenerPosicion(): Result<Posicion>
 fun playServicesDisponible(): Boolean
}
class GpsNoDisponibleException : Exception("No se pudo obtener la ubicación.\nActiva el GPS e intenta de nuevo")
