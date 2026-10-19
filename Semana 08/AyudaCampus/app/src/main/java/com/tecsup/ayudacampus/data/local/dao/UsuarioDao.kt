package com.tecsup.ayudacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tecsup.ayudacampus.data.local.entity.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): Usuario?

    @Insert suspend fun insertarTodos(lista: List<Usuario>)

    @Insert suspend fun insertar(u: Usuario): Long

    @Query("SELECT COUNT(*) FROM usuarios WHERE correo = :correo")
    suspend fun contarCorreo(correo: String): Int

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contar(): Int

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun obtener(id: Int): Usuario?

    @Query("SELECT * FROM usuarios WHERE rol = :rol ORDER BY id LIMIT 1")
    suspend fun primeroConRol(rol: String): Usuario?
}
