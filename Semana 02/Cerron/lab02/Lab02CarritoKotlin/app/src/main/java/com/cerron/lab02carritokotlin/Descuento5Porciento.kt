package com.cerron.lab02carritokotlin

class Descuento5Porciento : PoliticaDescuento {

    override fun calcular(total: Double): Double {
        return total * 0.05
    }

    override fun descripcion(): String {
        return "Descuento del 5%"
    }
}