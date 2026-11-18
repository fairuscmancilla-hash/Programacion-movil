package com.cerron.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.cerron.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {
 private val contexto = context.applicationContext
 private val cliente = LocationServices.getFusedLocationProviderClient(contexto)
 override fun playServicesDisponible() = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(contexto) == ConnectionResult.SUCCESS
 @SuppressLint("MissingPermission")
 override suspend fun obtenerPosicion(): Result<Posicion> {
  val token = CancellationTokenSource()
  return try {
   val manager = contexto.getSystemService(Context.LOCATION_SERVICE) as LocationManager
   if (!LocationManagerCompat.isLocationEnabled(manager)) throw GpsNoDisponibleException()
   val ubicacion = withTimeoutOrNull(20000) {
    cliente.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token).await()
   } ?: throw GpsNoDisponibleException()
   Result.success(Posicion(ubicacion.latitude, ubicacion.longitude))
  } catch (e: CancellationException) { throw e
  } catch (e: Exception) { Result.failure(e)
  } finally { token.cancel() }
 }
}
