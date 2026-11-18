package com.cerron.campusgps.util

import java.util.Locale
import kotlin.math.roundToInt
fun formatearDistancia(metros: Float): String =
 if (metros < 1000) "${metros.roundToInt()} m" else String.format(Locale.US, "%.1f km", metros / 1000)
fun formatearCoordenada(valor: Double): String = String.format(Locale.US, "%.4f", valor)
