package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.entity.Categoria
import kotlinx.coroutines.flow.Flow

interface AyudaRepository {
    // TODO (Paso 4): Usuarios — login(correo, clave): Usuario?

    // Categorías (ejemplo resuelto)
    fun observarCategorias(): Flow<List<Categoria>>
    fun observarCategoriasActivas(): Flow<List<Categoria>>
    suspend fun guardarCategoria(c: Categoria)
    suspend fun eliminarCategoria(c: Categoria)
    suspend fun cambiarActiva(id: Int, activa: Boolean)

    // TODO (Paso 4): Tickets y Panel (necesitan Usuario, Ticket, Comentario y TicketConDetalle)
}
