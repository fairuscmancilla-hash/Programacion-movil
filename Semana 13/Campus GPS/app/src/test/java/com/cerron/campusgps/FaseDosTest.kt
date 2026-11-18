package com.cerron.campusgps

import com.cerron.campusgps.model.*
import com.cerron.campusgps.util.filtrarPorCategoria
import org.junit.Assert.*
import org.junit.Test
class FaseDosTest {
 private val lugares = listOf(
  LugarConDistancia(Lugar(1, "Biblioteca", "Estudio", 0.0, 0.0), 50f),
  LugarConDistancia(Lugar(2, "Cafetería", "Comida", 0.0, 0.0), 150f),
  LugarConDistancia(Lugar(3, "Laboratorio", "Estudio", 0.0, 0.0), null))
 @Test fun filtraSoloLaCategoriaElegida() {
  assertEquals(listOf(1, 3), filtrarPorCategoria(lugares, "Estudio").map { it.lugar.id })
 }
 @Test fun sinCategoriaMuestraTodos() {
  assertEquals(3, filtrarPorCategoria(lugares, null).size)
 }
}
