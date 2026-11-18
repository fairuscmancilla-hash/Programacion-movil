package com.cerron.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.cerron.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {
 private val cliente = LocationServices.getFusedLocationProviderClient(context.applicationContext)
 @SuppressLint("MissingPermission")
 override suspend fun obtenerPosicion(): Result<Posicion> {
  val token = CancellationTokenSource()
  return try {
   val ubicacion = cliente.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token).await()
   if (ubicacion != null) Result.success(Posicion(ubicacion.latitude, ubicacion.longitude))
   else Result.failure(Exception("No se pudo obtener la ubicación"))
  } catch (e: CancellationException) { throw e
  } catch (e: Exception) { Result.failure(e)
  } finally { token.cancel() }
 }
}
