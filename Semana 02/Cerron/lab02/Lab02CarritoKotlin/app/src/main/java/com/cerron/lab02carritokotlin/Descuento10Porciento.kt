package com.cerron.lab02carritokotlin

class Descuento10Porciento : PoliticaDescuento {

    override fun calcular(total: Double): Double {
        return total * 0.10
    }

    override fun descripcion(): String {
        return "Descuento del 10%"
    }
}

