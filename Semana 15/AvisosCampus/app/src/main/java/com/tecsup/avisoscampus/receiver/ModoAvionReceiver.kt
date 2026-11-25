package com.tecsup.avisoscampus.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

// Paso 6: escucha el anuncio del sistema cuando cambia el modo avión.
// onReceive es corto: solo lee el extra y entrega el valor a quien lo registró.
class ModoAvionReceiver(private val alCambiar: (Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            alCambiar(intent.getBooleanExtra("state", false))
        }
    }
}
