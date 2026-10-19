package com.tecsup.ayudacampus.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.tecsup.ayudacampus.MainActivity
import com.tecsup.ayudacampus.model.Aviso

private const val CANAL_AVISOS = "avisos_tickets"

fun puedeNotificar(context: Context): Boolean =
    Build.VERSION.SDK_INT < 33 ||
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

/** Publica el aviso como notificación del sistema (si el usuario dio permiso). */
@SuppressLint("MissingPermission")
fun publicarNotificacion(context: Context, aviso: Aviso) {
    if (!puedeNotificar(context)) return
    val manager = NotificationManagerCompat.from(context)
    manager.createNotificationChannel(
        NotificationChannelCompat.Builder(CANAL_AVISOS, NotificationManagerCompat.IMPORTANCE_DEFAULT)
            .setName("Avisos de tickets")
            .build()
    )
    val abrirApp = PendingIntent.getActivity(
        context, 0,
        Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val notificacion = NotificationCompat.Builder(context, CANAL_AVISOS)
        .setSmallIcon(android.R.drawable.stat_notify_chat)
        .setContentTitle(aviso.titulo)
        .setContentText(aviso.detalle)
        .setStyle(NotificationCompat.BigTextStyle().bigText(aviso.detalle))
        .setContentIntent(abrirApp)
        .setAutoCancel(true)
        .build()
    manager.notify((aviso.fecha % Int.MAX_VALUE).toInt(), notificacion)
}
