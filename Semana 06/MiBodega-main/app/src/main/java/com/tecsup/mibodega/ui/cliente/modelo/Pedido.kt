package com.tecsup.mibodega.ui.cliente.modelo

data class Pedido(
    val id: Int,
    val productos: List<ItemCarrito>,
    val total: Double,
    val nombreCliente: String,
    val direccion: String,
    val estado: String = "Confirmado"
)