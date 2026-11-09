package com.tecsup.catalogo.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "items")
data class ItemEntity(@PrimaryKey val id: Int, val titulo: String, val descripcion: String, val precio: Double, val imagenUrl: String, val marca: String, val rating: Double, val stock: Int, val esFavorito: Boolean = false)
