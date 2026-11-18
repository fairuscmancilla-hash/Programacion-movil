package com.cerron.campusgps.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
private val Context.almacen by preferencesDataStore("campus_gps")
class UltimoLugarRepositoryDataStore(context: Context) : UltimoLugarRepository {
 private val almacen = context.applicationContext.almacen
 private val clave = intPreferencesKey("ultimo_lugar_id")
 override val ultimoLugarId: Flow<Int?> = almacen.data
  .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
  .map { it[clave] }
 override suspend fun guardar(id: Int) {
  almacen.edit { it[clave] = id }
 }
}
