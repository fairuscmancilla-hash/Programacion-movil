package com.tecsup.avisoscampus

import android.app.Application
import com.tecsup.avisoscampus.notificacion.Notificador

// Paso 1: la app crea el canal "Avisos" una sola vez, al iniciar.
class AvisosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}
