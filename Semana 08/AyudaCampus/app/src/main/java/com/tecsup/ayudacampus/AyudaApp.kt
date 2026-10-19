package com.tecsup.ayudacampus

import android.app.Application
import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.DatosIniciales
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
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
    lateinit var preferencias: PreferenciasStore
        private set

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        database = AyudaDatabase.obtener(this)
        repository = AyudaRepositoryRoom(database)
        preferencias = PreferenciasStore(this)
        appScope.launch { DatosIniciales.sembrar(database) }
    }
}
