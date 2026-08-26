package com.cerron.lab02carritokotlin

interface PoliticaDescuento {
    fun calcular(total: Double): Double
    fun descripcion(): String
}
