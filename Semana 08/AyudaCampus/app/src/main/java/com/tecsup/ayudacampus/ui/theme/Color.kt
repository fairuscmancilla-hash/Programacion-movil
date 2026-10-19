package com.tecsup.ayudacampus.ui.theme

import androidx.compose.ui.graphics.Color
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad

// Estudiante
val Cian = Color(0xFF0891B2)
val CianContenedor = Color(0xFFCFFAFE)
val CianTinta = Color(0xFF164E63)
val FondoEstudiante = Color(0xFFF7FDFE)
val CianClaro = Color(0xFF67E8F9)

// Soporte TI
val Violeta = Color(0xFF7C3AED)
val VioletaContenedor = Color(0xFFEDE4FF)
val VioletaTinta = Color(0xFF3B0764)
val FondoSoporte = Color(0xFFFAF8FF)
val VioletaClaro = Color(0xFFB79CFF)

// Estado del ticket
val EstadoAbierto = Color(0xFFD97706)
val EstadoEnProceso = Color(0xFF2563EB)
val EstadoResuelto = Color(0xFF16A34A)
val EstadoCerrado = Color(0xFF6B7280)

// Prioridad
val PrioridadAlta = Color(0xFFDC2626)
val PrioridadMedia = Color(0xFFB45309)
val PrioridadBaja = Color(0xFF6B7280)

// Otros
val Estrella = Color(0xFFF5B301)
val Tinta = Color(0xFF0F172A)
val TintaSuave = Color(0xFF64748B)
val Borde = Color(0xFFE2E8F0)

fun EstadoTicket.color(): Color = when (this) {
    EstadoTicket.ABIERTO -> EstadoAbierto
    EstadoTicket.EN_PROCESO -> EstadoEnProceso
    EstadoTicket.RESUELTO -> EstadoResuelto
    EstadoTicket.CERRADO -> EstadoCerrado
}

fun Prioridad.color(): Color = when (this) {
    Prioridad.ALTA -> PrioridadAlta
    Prioridad.MEDIA -> PrioridadMedia
    Prioridad.BAJA -> PrioridadBaja
}
