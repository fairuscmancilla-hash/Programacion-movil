package com.cerron.lab02carritokotlin

class CarritoCompras {

    private val productos = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        productos.add(producto)
    }

    fun buscarProducto(nombre: String): Producto? {
        return productos.find { it.nombre == nombre }
    }

    fun eliminarProducto(nombre: String): Boolean {
        val producto = productos.find { it.nombre == nombre }

        return if (producto != null) {
            productos.remove(producto)
            true
        } else {
            false
        }
    }

    fun obtenerProductos(): List<Producto> {
        return productos
    }

    fun calcularSubtotal(): Double {
        var subtotal = 0.0
        for (producto in productos) {
            subtotal += producto.precio * producto.cantidad
        }
        return subtotal
    }

    fun calcularIGV(): Double {
        return calcularSubtotal() * 0.18
    }

    fun calcularTotal(): Double {
        return calcularSubtotal() + calcularIGV()
    }

    fun aplicarDescuento(politica: PoliticaDescuento): Double {
        val total = calcularTotal()
        return politica.calcular(total)
    }

    fun seleccionarPoliticaDescuento(): PoliticaDescuento {
        val total = calcularTotal()

        return when {
            total >= 3000 -> Descuento10Porciento()
            total >= 1000 -> Descuento5Porciento()
            else -> SinDescuento()
        }
    }

    fun mostrarDetalle() {
        println("--------- DETALLE DEL CARRITO ---------")
        var i = 1

        for (producto in productos) {
            val importe = producto.precio * producto.cantidad

            println(
                String.format(
                    "%d. %-20s x%d S/ %8.2f",
                    i,
                    producto.nombre,
                    producto.cantidad,
                    importe
                )
            )

            i++
        }

        println("---------------------------------------")
    }

}