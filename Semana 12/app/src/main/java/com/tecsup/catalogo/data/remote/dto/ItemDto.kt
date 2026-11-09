package com.tecsup.catalogo.data.remote.dto
import kotlinx.serialization.Serializable

/** DummyJSON envuelve la lista en "products". */
@Serializable
data class ProductosDto(val products: List<ItemDto>)

@Serializable
data class ItemDto(val id: Int, val title: String, val description: String, val price: Double, val thumbnail: String, val brand: String? = null, val rating: Double = 0.0, val stock: Int = 0)
