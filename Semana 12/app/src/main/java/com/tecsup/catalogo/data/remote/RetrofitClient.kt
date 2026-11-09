package com.tecsup.catalogo.data.remote
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {
 private const val BASE_URL = "https://dummyjson.com/" // debe terminar en /
 // La API envía más campos de los que usa el DTO: se ignoran.
 private val json = Json { ignoreUnknownKeys = true }
 private val cliente = OkHttpClient.Builder()
  .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
  .build()
 fun crear(): ApiService = Retrofit.Builder()
  .baseUrl(BASE_URL)
  .client(cliente)
  .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
  .build()
  .create(ApiService::class.java)
}
