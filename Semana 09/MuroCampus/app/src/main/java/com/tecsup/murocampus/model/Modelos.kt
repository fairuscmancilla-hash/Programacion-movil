package com.tecsup.murocampus.model

import kotlinx.serialization.Serializable

// [E] Ejemplo resuelto: GET users
@Serializable
data class Usuario(
    val id: Int,
    val name: String,
    val email: String
)

// Paso 2: GET posts / posts/{id}
@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

// Paso 2: GET posts/{id}/comments
@Serializable
data class Comentario(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

// Paso 2: cuerpo del POST posts. Lo que enviamos no lleva id: el servidor lo asigna.
@Serializable
data class NuevoPost(
    val userId: Int,
    val title: String,
    val body: String
)
