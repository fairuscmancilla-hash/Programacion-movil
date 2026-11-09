package com.tecsup.catalogo.domain.model
data class Item(val id: Int, val titulo: String, val descripcion: String, val precio: Double, val imagenUrl: String, val marca: String, val rating: Double, val stock: Int, val esFavorito: Boolean = false)
