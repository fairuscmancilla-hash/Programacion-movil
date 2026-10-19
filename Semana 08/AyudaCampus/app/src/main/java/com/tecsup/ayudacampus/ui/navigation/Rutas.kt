package com.tecsup.ayudacampus.ui.navigation

object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTRO = "registro"

    // Grafo del estudiante
    const val GRAFO_CLIENTE = "cliente"
    const val MIS_TICKETS = "mis_tickets"
    const val NUEVO_TICKET = "nuevo_ticket"
    const val DETALLE = "detalle/{ticketId}"
    const val PERFIL = "perfil"
    fun detalle(id: Int) = "detalle/$id"

    // Grafo de Soporte TI
    const val GRAFO_SOPORTE = "soporte"
    const val PANEL = "panel"
    const val BANDEJA = "bandeja?estado={estado}"
    const val ATENDER = "atender/{ticketId}"
    const val CATEGORIAS = "categorias"
    const val FORM_CATEGORIA = "form_categoria/{categoriaId}"
    const val AJUSTES = "ajustes"
    fun bandeja(estado: String = "") = "bandeja?estado=$estado"
    fun atender(id: Int) = "atender/$id"
    fun formCategoria(id: Int) = "form_categoria/$id"
}
