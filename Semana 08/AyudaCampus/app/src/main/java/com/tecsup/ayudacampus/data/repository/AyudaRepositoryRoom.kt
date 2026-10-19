package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.entity.Categoria
import kotlinx.coroutines.flow.Flow

class AyudaRepositoryRoom(db: AyudaDatabase) : AyudaRepository {

    private val categoriaDao = db.categoriaDao()
    // TODO (Paso 4): usuarioDao y ticketDao

    // Categorías (ejemplo resuelto)
    override fun observarCategorias(): Flow<List<Categoria>> = categoriaDao.observarTodas()

    override fun observarCategoriasActivas(): Flow<List<Categoria>> = categoriaDao.observarActivas()

    override suspend fun guardarCategoria(c: Categoria) {
        if (c.id == 0) categoriaDao.insertar(c) else categoriaDao.actualizar(c)
    }

    override suspend fun eliminarCategoria(c: Categoria) = categoriaDao.eliminar(c)

    override suspend fun cambiarActiva(id: Int, activa: Boolean) = categoriaDao.cambiarActiva(id, activa)

    // TODO (Paso 4): implementar el resto de la interface (login, tickets, comentarios y panel)
}
