package com.tecsup.avisoscampus

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tecsup.avisoscampus.data.AvisosRepository
import com.tecsup.avisoscampus.data.AvisosRepositoryDataStore
import com.tecsup.avisoscampus.notificacion.Notificador

// Mejora 3: un solo DataStore para toda la app (archivo "historial_avisos").
private val Context.historialDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "historial_avisos"
)

// Paso 1: la app crea el canal "Avisos" una sola vez, al iniciar.
class AvisosApp : Application() {

    // Mejora 3: la Application crea el Repository y le entrega el DataStore.
    // Así el Repository y el ViewModel nunca reciben un Context.
    val repositorio: AvisosRepository by lazy {
        AvisosRepositoryDataStore(historialDataStore)
    }

    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}
