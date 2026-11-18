package com.cerron.campusgps.util

import com.cerron.campusgps.model.LugarConDistancia
fun filtrarPorCategoria(lugares: List<LugarConDistancia>, categoria: String?): List<LugarConDistancia> =
 if (categoria == null) lugares else lugares.filter { it.lugar.categoria == categoria }
