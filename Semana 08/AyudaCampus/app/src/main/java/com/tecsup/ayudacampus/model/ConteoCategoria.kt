package com.tecsup.ayudacampus.model

// Resultado del LEFT JOIN categorias-tickets (no es una tabla)
data class ConteoCategoria(
    val categoriaId: Int,
    val nombre: String,
    val icono: String,
    val cantidad: Int
)
