package com.tecsup.ayudacampus.model

enum class Rol(val etiqueta: String) {
    CLIENTE("Estudiante"),
    ADMIN("Soporte TI");

    fun otro(): Rol = if (this == CLIENTE) ADMIN else CLIENTE
}
