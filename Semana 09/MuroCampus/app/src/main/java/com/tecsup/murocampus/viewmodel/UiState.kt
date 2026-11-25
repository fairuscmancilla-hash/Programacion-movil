package com.tecsup.murocampus.viewmodel

import com.tecsup.murocampus.data.ErrorApp

// [E] Archivo entregado en el proyecto base: estados de una pantalla que carga datos.
sealed interface UiState<out T> {
    data object Cargando : UiState<Nothing>
    data class Exito<T>(val datos: T) : UiState<T>
    data class Error(val mensaje: String) : UiState<Nothing>
}

/**
 * Texto que ve el usuario para cada error. Recibe los ErrorApp que arma el Repository,
 * asi esta carpeta no depende de la libreria de red (revision del Paso 10).
 */
fun mensajeDe(e: Throwable): String = when (e) {
    is ErrorApp.SinConexion -> "Sin conexión a internet.\nRevisa tu red e inténtalo otra vez."
    is ErrorApp.Servidor -> "Error del servidor (${e.codigo})"
    is ErrorApp.RespuestaInesperada -> "Respuesta inesperada"
    else -> "Error desconocido"
}
