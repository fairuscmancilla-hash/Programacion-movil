package com.tecsup.ayudacampus.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DesktopWindows
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

val ICONOS_CATEGORIA = listOf("pc", "code", "wifi", "key", "printer", "tag")

fun iconoCategoria(icono: String?): ImageVector = when (icono) {
    "pc" -> Icons.Outlined.DesktopWindows
    "code" -> Icons.Outlined.Code
    "wifi" -> Icons.Outlined.Wifi
    "key" -> Icons.Outlined.Key
    "printer" -> Icons.Outlined.Print
    else -> Icons.Outlined.LocalOffer
}

fun numeroTicket(id: Int): String = "#%04d".format(id)

fun tiempoRelativo(fecha: Long, ahora: Long = System.currentTimeMillis()): String {
    val minutos = (ahora - fecha) / 60_000
    return when {
        minutos < 1 -> "Ahora"
        minutos < 60 -> "Hace $minutos min"
        minutos < 24 * 60 -> "Hace ${minutos / 60} h"
        minutos < 48 * 60 -> "Ayer"
        else -> "Hace ${minutos / (24 * 60)} d"
    }
}

/** Hora si es de hoy, "ayer" o la fecha corta. */
fun momento(fecha: Long): String {
    val hoy = Calendar.getInstance()
    val dia = Calendar.getInstance().apply { timeInMillis = fecha }
    val mismoAnio = hoy.get(Calendar.YEAR) == dia.get(Calendar.YEAR)
    val diff = hoy.get(Calendar.DAY_OF_YEAR) - dia.get(Calendar.DAY_OF_YEAR)
    return when {
        mismoAnio && diff == 0 -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(fecha))
        mismoAnio && diff == 1 -> "ayer"
        else -> fechaCorta(fecha)
    }
}

fun fechaCorta(fecha: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(fecha))

/** 90 min -> "1 h 30 min"; 50 h -> "2 d 2 h" */
fun duracion(milis: Long): String {
    val minutos = (milis / 60_000).coerceAtLeast(0)
    val dias = minutos / (24 * 60)
    val horas = (minutos / 60) % 24
    val min = minutos % 60
    return when {
        dias > 0 -> "$dias d $horas h"
        minutos >= 60 -> "${minutos / 60} h $min min"
        else -> "$min min"
    }
}

/** "Juan Pérez" -> "Juan P." */
fun nombreCorto(nombre: String?): String {
    if (nombre.isNullOrBlank()) return "—"
    val partes = nombre.trim().split(" ")
    return if (partes.size == 1) partes[0] else "${partes[0]} ${partes[1].first()}."
}
