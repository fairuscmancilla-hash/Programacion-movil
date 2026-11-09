package com.tecsup.catalogo.ui
import java.io.IOException
import retrofit2.HttpException

/** Convierte un error técnico en un mensaje claro para la persona. */
fun mensajeDeError(e: Throwable): String = when (e) {
 is IOException -> "No hay conexión a internet"
 is HttpException -> if (e.code() == 404) "No se encontró el elemento solicitado" else "El servidor respondió con error ${e.code()}"
 else -> "Ocurrió un error inesperado"
}
