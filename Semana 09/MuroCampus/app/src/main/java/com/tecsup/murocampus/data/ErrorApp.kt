package com.tecsup.murocampus.data

import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

/**
 * Errores propios de la app. El Repository traduce aqui las excepciones de la red
 * (IOException, HttpException de Retrofit, SerializationException) para que el
 * ViewModel nunca tenga que conocer Retrofit ni HttpException.
 */
sealed class ErrorApp(causa: Throwable) : Exception(causa.message, causa) {
    class SinConexion(causa: Throwable) : ErrorApp(causa)
    class Servidor(val codigo: Int, causa: Throwable) : ErrorApp(causa)
    class RespuestaInesperada(causa: Throwable) : ErrorApp(causa)
    class Desconocido(causa: Throwable) : ErrorApp(causa)
}

/** Convierte cualquier excepcion de la capa de datos en un ErrorApp. */
fun Throwable.aErrorApp(): ErrorApp = when (this) {
    is ErrorApp -> this
    is IOException -> ErrorApp.SinConexion(this)
    is HttpException -> ErrorApp.Servidor(code(), this)
    is SerializationException -> ErrorApp.RespuestaInesperada(this)
    else -> ErrorApp.Desconocido(this)
}
