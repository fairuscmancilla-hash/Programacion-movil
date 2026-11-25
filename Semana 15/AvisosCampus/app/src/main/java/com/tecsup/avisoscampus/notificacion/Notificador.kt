package com.tecsup.avisoscampus.notificacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.tecsup.avisoscampus.MainActivity
import com.tecsup.avisoscampus.R
import com.tecsup.avisoscampus.model.Aviso

object Notificador {

    const val CANAL = "avisos"
    const val CANAL_URGENTE = "urgentes"

    // Paso 1 + Mejora 1: los canales solo existen desde Android 8 (API 26, "O").
    // "Urgentes" usa IMPORTANCE_HIGH: la notificación aparece emergente sobre la pantalla.
    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CANAL,
                "Avisos",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val urgente = NotificationChannel(
                CANAL_URGENTE,
                "Urgentes",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Recordatorios que no debes pasar por alto"
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannels(listOf(canal, urgente))
        }
    }

    // Paso 2: antes de Android 13 (API 33) el permiso se concede al instalar.
    fun tienePermiso(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Pasos 3 y 4: construye y muestra la notificación; devuelve false si no hay permiso.
    // Mejora 1: si urgente es true, usa el canal Urgentes y prioridad alta (para Android < 8).
    // Mejora 2: detalle es el texto completo que se ve al expandir (BigTextStyle).
    @SuppressLint("MissingPermission")
    fun mostrar(
        context: Context,
        aviso: Aviso,
        urgente: Boolean = false,
        detalle: String? = null
    ): Boolean {
        // 1. Sin permiso no se muestra nada, pero tampoco se lanza error
        if (!tienePermiso(context)) return false

        // Paso 4: al tocar la notificación se abre MainActivity
        val intent = Intent(context, MainActivity::class.java)
        val pendiente = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // 2. Construir la notificación
        val canal = if (urgente) CANAL_URGENTE else CANAL
        val prioridad =
            if (urgente) NotificationCompat.PRIORITY_HIGH else NotificationCompat.PRIORITY_DEFAULT
        val notificacion = NotificationCompat.Builder(context, canal)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(aviso.titulo)
            .setContentText(aviso.texto)
            // Mejora 2: contraída muestra aviso.texto; expandida, el detalle completo
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(detalle ?: aviso.texto)
                    .setSummaryText(aviso.origen.texto)
            )
            .setPriority(prioridad)
            .setContentIntent(pendiente)
            .setAutoCancel(true)
            .build()

        // 3. El id del aviso hace que cada notificación sea distinta
        NotificationManagerCompat.from(context).notify(aviso.id, notificacion)
        return true
    }
}
