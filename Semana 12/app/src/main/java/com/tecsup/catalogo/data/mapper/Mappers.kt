package com.tecsup.catalogo.data.mapper
import com.tecsup.catalogo.data.remote.dto.ItemDto
import com.tecsup.catalogo.data.local.ItemEntity
import com.tecsup.catalogo.domain.model.Item
fun ItemDto.aEntity(esFavorito: Boolean = false) = ItemEntity(id, title, description, price, thumbnail, brand ?: "Sin marca", rating, stock, esFavorito)
fun ItemEntity.aModelo() = Item(id, titulo, DescripcionesEspanol.obtener(titulo), precio, imagenUrl, marca, rating, stock, esFavorito)
