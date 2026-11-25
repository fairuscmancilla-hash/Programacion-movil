package com.tecsup.murocampus.data.remote

import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Paso 3: describe las llamadas al servicio. Retrofit genera la implementacion.
 * Las rutas NO empiezan con "/" para que se sumen a la URL base.
 */
interface PostApi {

    // GET posts -> lista de 100 publicaciones
    @GET("posts")
    suspend fun listar(): List<Post>

    // GET posts/{id} -> @Path reemplaza {id} dentro de la ruta
    @GET("posts/{id}")
    suspend fun detalle(@Path("id") id: Int): Post

    // GET posts/{id}/comments -> comentarios de una publicacion
    @GET("posts/{id}/comments")
    suspend fun comentarios(@Path("id") id: Int): List<Comentario>

    // GET posts?userId=2 -> @Query agrega un parametro despues del "?"
    @GET("posts")
    suspend fun porUsuario(@Query("userId") userId: Int): List<Post>

    // POST posts -> @Body convierte el objeto a JSON y lo envia en el cuerpo
    @POST("posts")
    suspend fun crear(@Body nuevo: NuevoPost): Post

    // Mejora 1 · DELETE posts/{id} -> no devuelve datos utiles; Response<Unit> deja revisar el codigo HTTP
    @DELETE("posts/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
