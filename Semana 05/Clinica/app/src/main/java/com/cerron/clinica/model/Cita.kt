package com.cerron.clinica.model

data class Cita(
    val medico: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: String = "Confirmada"
)