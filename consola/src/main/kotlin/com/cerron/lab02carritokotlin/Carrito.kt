package com.cerron.lab02carritokotlin

fun main() {

    println("==========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP")
    println("        VERSION POO CON IA")
    println("==========================================")

    val nombreCliente = "Yajaira Cerron"
    val carrito = CarritoCompras()

    println("Cliente: $nombreCliente")
    println()

    carrito.agregarProducto(Producto("Laptop HP", 2500.0, 1))
    carrito.agregarProducto(Producto("Mouse Logitech", 45.5, 2))
    carrito.agregarProducto(Producto("Teclado", 120.0, 1))
    carrito.agregarProducto(Producto("Audífonos Gamer", 85.5, 2))

    println("Productos agregados correctamente.")
    println()

    carrito.mostrarDetalle()

    println("Cantidad de productos: ${carrito.obtenerProductos().size}")

    val subtotal = carrito.calcularSubtotal()
    val igv = carrito.calcularIGV()
    val total = carrito.calcularTotal()

    println(String.format("Subtotal: S/ %.2f", subtotal))
    println(String.format("IGV (18%%): S/ %.2f", igv))
    println(String.format("TOTAL A PAGAR: S/ %.2f", total))

    val masCaro = carrito.obtenerProductos().maxByOrNull { it.precio }

    if (masCaro != null) {
        println(
            "Producto más caro: ${masCaro.nombre} " +
                    String.format("(S/ %.2f)", masCaro.precio)
        )
    }

    println()
    println("----- POLIMORFISMO: DESCUENTOS -----")

    val politica: PoliticaDescuento =
        carrito.seleccionarPoliticaDescuento()

    val descuento = carrito.aplicarDescuento(politica)
    val totalConDescuento = total - descuento

    println("Política aplicada: ${politica.descripcion()}")
    println(String.format("Descuento: S/ %.2f", descuento))
    println(String.format("TOTAL CON DESCUENTO: S/ %.2f", totalConDescuento))

    println()
    println("----- BUSQUEDA DE PRODUCTO -----")

    val encontrado = carrito.buscarProducto("Mouse Logitech")

    if (encontrado != null) {
        println("Producto encontrado: ${encontrado.nombre}")
    } else {
        println("Producto no encontrado")
    }

    println()
    println("----- ELIMINACION DE PRODUCTO -----")

    val eliminado = carrito.eliminarProducto("Teclado")

    if (eliminado) {
        println("Teclado eliminado correctamente.")
    } else {
        println("No se encontró el producto.")
    }

    println()
    println("----- CARRITO ACTUALIZADO -----")

    carrito.mostrarDetalle()

    println(
        String.format(
            "Nuevo total: S/ %.2f",
            carrito.calcularTotal()
        )
    )
}