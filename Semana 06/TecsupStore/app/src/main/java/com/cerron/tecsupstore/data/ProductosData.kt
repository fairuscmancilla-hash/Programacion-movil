package com.cerron.tecsupstore.data

import com.cerron.tecsupstore.model.Producto

val productos = listOf(
    Producto(
        id = 1,
        nombre = "Laptop ASUS",
        descripcion = "Laptop ideal para estudios y programación",
        precio = 2499.90,
        categoria = "Tecnología"
    ),
    Producto(
        id = 2,
        nombre = "Mouse Inalámbrico",
        descripcion = "Mouse ergonómico con conexión inalámbrica",
        precio = 59.90,
        categoria = "Accesorios"
    ),
    Producto(
        id = 3,
        nombre = "Teclado Mecánico",
        descripcion = "Teclado mecánico para programación",
        precio = 189.90,
        categoria = "Accesorios"
    ),
    Producto(
        id = 4,
        nombre = "Audífonos Bluetooth",
        descripcion = "Audífonos inalámbricos con sonido estéreo",
        precio = 129.90,
        categoria = "Tecnología"
    ),
    Producto(
        id = 5,
        nombre = "Mochila TECSUP",
        descripcion = "Mochila resistente para laptop y accesorios",
        precio = 89.90,
        categoria = "Otros"
    ),
    Producto(
        id = 6,
        nombre = "USB 64 GB",
        descripcion = "Memoria USB de alta velocidad",
        precio = 39.90,
        categoria = "Tecnología"
    )
)

val categorias = listOf(
    "Todos",
    "Tecnología",
    "Accesorios",
    "Otros"
)