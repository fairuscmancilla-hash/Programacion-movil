package com.tecsup.ayudacampus.model

enum class EstadoTicket(val etiqueta: String, val plural: String) {
    ABIERTO("Abierto", "Abiertos"),
    EN_PROCESO("En proceso", "En proceso"),
    RESUELTO("Resuelto", "Resueltos"),
    CERRADO("Cerrado", "Cerrados")
}
