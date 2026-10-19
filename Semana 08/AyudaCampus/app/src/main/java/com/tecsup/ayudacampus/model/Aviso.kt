package com.tecsup.ayudacampus.model

/** Un aviso de la campana (nuevo ticket, respuesta, cambio de estado). No es una tabla. */
data class Aviso(
    val ticketId: Int,
    val titulo: String,
    val detalle: String,
    val fecha: Long,
    val leido: Boolean = false
)
