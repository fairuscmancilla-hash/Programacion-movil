package com.tecsup.avisoscampus.model

enum class Origen(val texto: String) {
    BOTON("Botón"),
    RECORDATORIO("Recordatorio"),
    MODO_AVION("Modo avión")
}

data class Aviso(
    val id: Int,
    val titulo: String,
    val texto: String,
    val origen: Origen,
    val hora: String
)

// Mejora 1: evento que el ViewModel emite. Decide si el aviso es urgente,
// pero no sabe nada de canales: eso lo resuelve el Notificador.
// Mejora 2: detalle es el texto largo que se ve al expandir la notificación.
data class EventoAviso(
    val aviso: Aviso,
    val urgente: Boolean = false,
    val detalle: String? = null
)

sealed interface PermisoUi {
    data object SinPedir : PermisoUi
    data object Concedido : PermisoUi
    data object Denegado : PermisoUi
}
