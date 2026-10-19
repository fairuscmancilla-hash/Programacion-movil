package com.tecsup.ayudacampus

import android.app.Application
import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.DatosIniciales
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.data.repository.AyudaRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AyudaApp : Application() {

    lateinit var database: AyudaDatabase
        private set
    lateinit var repository: AyudaRepository
        private set
    // TODO (Paso 5): crear aquí PreferenciasStore

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        database = AyudaDatabase.obtener(this)
        repository = AyudaRepositoryRoom(database)
        appScope.launch { DatosIniciales.sembrar(database) }
    }
}
