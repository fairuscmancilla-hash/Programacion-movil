package com.tecsup.catalogo.data.mapper
/** Traducciones locales del catálogo smartphones: disponibles también sin conexión.
 * Los nombres comerciales se mantienen. Un producto nuevo no recibe especificaciones inventadas.
 */
internal object DescripcionesEspanol {
 private val traducciones = mapOf(
  "iPhone 5s" to "El iPhone 5s es un teléfono inteligente clásico, conocido por su diseño compacto y las funciones avanzadas que ofrecía en su lanzamiento. Aunque es un modelo antiguo, sigue ofreciendo una experiencia de uso confiable.",
  "iPhone 6" to "El iPhone 6 combina un diseño elegante con una pantalla más grande y un rendimiento mejorado. Incorporó nuevas funciones y elementos de diseño que lo convirtieron en una opción popular en su época.",
  "iPhone 13 Pro" to "El iPhone 13 Pro incorpora un potente sistema de cámaras, un chip de alto rendimiento y una pantalla de gran calidad. Ofrece funciones avanzadas para quienes buscan tecnología de primer nivel.",
  "iPhone X" to "El iPhone X es un teléfono de gama alta con pantalla OLED sin bordes, reconocimiento facial Face ID y un gran rendimiento. Representa un hito en el diseño y la innovación del iPhone.",
  "Oppo A57" to "El Oppo A57 es un teléfono de gama media con un diseño elegante y funciones prácticas. Ofrece un equilibrio entre rendimiento y precio accesible.",
  "Oppo F19 Pro Plus" to "El Oppo F19 Pro Plus ofrece numerosas funciones, especialmente orientadas a la cámara. Combina herramientas avanzadas de fotografía con un rendimiento potente para una experiencia de uso de alta calidad.",
  "Oppo K1" to "La serie Oppo K1 reúne teléfonos con distintas funciones y especificaciones. Se caracteriza por su diseño elegante y rendimiento confiable, con opciones para diferentes preferencias.",
  "Realme C35" to "El Realme C35 es un teléfono económico que ofrece las funciones esenciales para el uso diario. Brinda un rendimiento confiable y una experiencia sencilla de utilizar.",
  "Realme X" to "El Realme X es un teléfono de gama media con diseño elegante y una pantalla de gran calidad. Ofrece un buen equilibrio entre rendimiento y funciones de cámara.",
  "Realme XT" to "El Realme XT destaca por sus funciones de fotografía. Sus sensores de cámara avanzados permiten capturar fotos y videos de alta calidad para los aficionados a la fotografía.",
  "Samsung Galaxy S7" to "El Samsung Galaxy S7 es un teléfono de gama alta con diseño elegante y funciones avanzadas. Cuenta con una pantalla de alta resolución, una cámara potente y un rendimiento sólido.",
  "Samsung Galaxy S8" to "El Samsung Galaxy S8 es un teléfono de gama alta con pantalla Infinity Display, que ofrece una gran experiencia visual. Incluye funciones avanzadas de cámara y tecnología de última generación para su época.",
  "Samsung Galaxy S10" to "El Samsung Galaxy S10 es un teléfono de gama alta con pantalla Dynamic AMOLED, un sistema de cámaras versátil y un rendimiento potente. Destaca por su propuesta de innovación tecnológica.",
  "Vivo S1" to "El Vivo S1 es un teléfono de gama media que combina diseño y rendimiento. Cuenta con una pantalla de colores vivos, un sistema de cámaras versátil y un funcionamiento confiable.",
  "Vivo V9" to "El Vivo V9 destaca por su diseño elegante y sus funciones para tomar autorretratos de alta calidad. Incluye una pantalla con muesca, un sistema de doble cámara y un diseño moderno.",
  "Vivo X21" to "El Vivo X21 es un teléfono de gama alta orientado a la tecnología avanzada. Cuenta con un lector de huellas integrado en la pantalla, una pantalla de alta resolución y funciones avanzadas de cámara."
 )
 fun obtener(titulo: String): String = traducciones[titulo]
  ?: "La descripción en español de este producto aún no está disponible. Puedes consultar su marca, valoración, precio y unidades disponibles."
}
