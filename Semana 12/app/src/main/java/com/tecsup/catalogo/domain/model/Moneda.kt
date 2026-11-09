package com.tecsup.catalogo.domain.model
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale
/** Room y la API conservan USD. Solo se convierte para presentar el precio. */
object Moneda {
 private val solesPorDolar = BigDecimal("3.45")
 const val aviso = "Precio referencial en soles. Conversión de prueba: US$ 1 = S/ 3.45; no es una cotización en vivo."
 fun enSoles(dolares: Double): BigDecimal = BigDecimal.valueOf(dolares).multiply(solesPorDolar).setScale(2, RoundingMode.HALF_UP)
 fun formatearSoles(dolares: Double): String {
  val formato=NumberFormat.getNumberInstance(Locale.forLanguageTag("es-PE")).apply { minimumFractionDigits=2; maximumFractionDigits=2 }
  return "S/ " + formato.format(enSoles(dolares))
 }
}
