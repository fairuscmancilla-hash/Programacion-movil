package com.tecsup.ayudacampus.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Una sola vez, a nivel de archivo
val Context.dataStore: DataStore<Preferences> by preferencesDataStore("ajustes")

class PreferenciasStore(private val context: Context) {

    private object Claves {
        val PERFIL_ACTIVO = stringPreferencesKey("perfilActivo")
        val USUARIO_ID = intPreferencesKey("usuarioId")
        val MODO_OSCURO = booleanPreferencesKey("modoOscuro")
        val NOTIFICACIONES = booleanPreferencesKey("notificaciones")
        val RECIBIR_TICKETS = booleanPreferencesKey("recibirTickets")
    }

    // Lectura: un Flow por preferencia
    val perfilActivo: Flow<String> = context.dataStore.data.map { it[Claves.PERFIL_ACTIVO] ?: "" }
    val usuarioId: Flow<Int> = context.dataStore.data.map { it[Claves.USUARIO_ID] ?: 0 }
    val modoOscuro: Flow<Boolean> = context.dataStore.data.map { it[Claves.MODO_OSCURO] ?: false }
    val notificaciones: Flow<Boolean> = context.dataStore.data.map { it[Claves.NOTIFICACIONES] ?: true }
    val recibirTickets: Flow<Boolean> = context.dataStore.data.map { it[Claves.RECIBIR_TICKETS] ?: true }

    // Escritura
    suspend fun guardarSesion(rol: String, usuarioId: Int) {
        context.dataStore.edit {
            it[Claves.PERFIL_ACTIVO] = rol
            it[Claves.USUARIO_ID] = usuarioId
        }
    }

    suspend fun guardarModoOscuro(b: Boolean) {
        context.dataStore.edit { it[Claves.MODO_OSCURO] = b }
    }

    suspend fun guardarNotificaciones(b: Boolean) {
        context.dataStore.edit { it[Claves.NOTIFICACIONES] = b }
    }

    suspend fun guardarRecibirTickets(b: Boolean) {
        context.dataStore.edit { it[Claves.RECIBIR_TICKETS] = b }
    }

    // Avisos de la campana, por perfil: hasta qué fecha se leyeron y hasta cuál ya salió notificación
    private fun claveVistos(rol: String) = longPreferencesKey("avisosVistos_$rol")
    private fun claveNotificados(rol: String) = longPreferencesKey("avisosNotificados_$rol")

    fun avisosVistos(rol: String): Flow<Long> = context.dataStore.data.map { it[claveVistos(rol)] ?: 0L }
    fun avisosNotificados(rol: String): Flow<Long> = context.dataStore.data.map { it[claveNotificados(rol)] ?: 0L }

    suspend fun guardarAvisosVistos(rol: String, fecha: Long) {
        context.dataStore.edit { it[claveVistos(rol)] = fecha }
    }

    suspend fun guardarAvisosNotificados(rol: String, fecha: Long) {
        context.dataStore.edit { it[claveNotificados(rol)] = fecha }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit {
            it.remove(Claves.PERFIL_ACTIVO)
            it.remove(Claves.USUARIO_ID)
        }
    }
}
