package com.tecsup.murocampus.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

/**
 * Paso 4: cliente Retrofit unico para toda la app (object = una sola instancia).
 */
object RetrofitClient {

    private const val URL_BASE = "https://jsonplaceholder.typicode.com/" // termina con "/"

    // ignoreUnknownKeys: si el servidor agrega un campo nuevo, la app no falla
    private val json = Json { ignoreUnknownKeys = true }

    // HttpLoggingInterceptor nivel BODY para ver el trafico en Logcat (filtro OkHttp)
    private val cliente = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(cliente)
            // Conversor kotlinx.serialization para JSON
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // Se crea una sola vez, la primera vez que se usa (by lazy)
    val api: PostApi by lazy { retrofit.create(PostApi::class.java) }

    // [E] Ejemplo resuelto de Usuario
    val usuarioApi: UsuarioApi by lazy { retrofit.create(UsuarioApi::class.java) }
}
