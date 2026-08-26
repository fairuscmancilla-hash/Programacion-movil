package com.cerron.lab02carritokotlin

class SinDescuento : PoliticaDescuento {

    override fun calcular(total: Double): Double {
        return 0.0
    }

    override fun descripcion(): String {
        return "Sin descuento"
    }
}