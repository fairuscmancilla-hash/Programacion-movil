package com.cerron.lab02carritokotlin

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (producto in productos) {
        subtotal += producto.precio * producto.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1

    for (p in productos) {
        val importe = p.precio * p.cantidad

        println(
            String.format(
                "%d. %-20s x%d S/ %8.2f",
                i, p.nombre, p.cantidad, importe
            )
        )

        i++
    }

    println("---------------------------------------")
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")


    val nombreCliente = "Yajaira Cerron" // String (inferido)
    val carrito = mutableListOf<Producto>() // lista vacía de productos

    println("Cliente: $nombreCliente")
    println()

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Teclado", 120.0, 1))
    carrito.add(Producto("Audífonos Gamer", 85.5, 2))

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("%-20s S/ %8.2f", "Subtotal:", subtotal))
    println(String.format("%-20s S/ %8.2f", "IGV (18%):", igv))
    println(String.format("%-20s S/ %8.2f", "TOTAL A PAGAR:", total))

    val masCaro = carrito.maxByOrNull { it.precio }

    if (masCaro != null) {
        println(
            "Producto mas caro: ${masCaro.nombre} " +
                    String.format("(S/ %.2f)", masCaro.precio)
        )
    }
    val descuento = calcularDescuento(total)
    val totalConDescuento = total - descuento

    println(String.format("Descuento aplicado: S/ %.2f", descuento))
    println(String.format("TOTAL CON DESCUENTO: S/ %.2f", totalConDescuento))

    val buscado = carrito.find { it.nombre == "Laptop HP" }

    if (buscado != null) {
        println("Producto encontrado: ${buscado.nombre}")
    } else {
        println("Producto no encontrado")
    }
    carrito.removeIf { it.nombre == "Teclado" }

    println()
    println("Producto eliminado: Teclado")

    mostrarDetalle(carrito)

    val nuevoSubtotal = calcularSubtotal(carrito)
    val nuevoIGV = calcularIGV(nuevoSubtotal)
    val nuevoTotal = calcularTotal(nuevoSubtotal, nuevoIGV)

    println(String.format("%-20s S/ %8.2f", "Subtotal actualizado:", nuevoSubtotal))
    println(String.format("%-20s S/ %8.2f", "IGV actualizado (18%):", nuevoIGV))
    println(String.format("%-20s S/ %8.2f", "TOTAL actualizado:", nuevoTotal))
}