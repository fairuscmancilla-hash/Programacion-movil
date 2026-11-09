package com.tecsup.catalogo.data.repository
import androidx.room.withTransaction
import com.tecsup.catalogo.data.local.AppDatabase
import com.tecsup.catalogo.data.remote.ApiService
import com.tecsup.catalogo.data.mapper.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.map
class ItemRepositoryImpl(private val api: ApiService, private val db: AppDatabase) : ItemRepository {
 private val dao = db.itemDao()
 override val items = dao.todos().map { it.map { e -> e.aModelo() } }
 override val favoritos = dao.favoritos().map { it.map { e -> e.aModelo() } }
 override suspend fun refrescar(): Result<Int> = seguro {
  val nuevos = api.listar().products
  db.withTransaction {
   val favoritos = dao.idsFavoritos().toSet()
   dao.guardarTodos(nuevos.map { it.aEntity(it.id in favoritos) })
  }
  nuevos.size
 }
 override suspend fun obtenerDetalle(id: Int) = seguro {
  try {
   val remoto = api.detalle(id)
   db.withTransaction { dao.guardarTodos(listOf(remoto.aEntity(dao.buscar(id)?.esFavorito ?: false))) }
  } catch (e: java.io.IOException) { if (dao.buscar(id) == null) throw e }
    catch (e: retrofit2.HttpException) { if (dao.buscar(id) == null) throw e }
  requireNotNull(dao.buscar(id)).aModelo()
 }
 override suspend fun alternarFavorito(id: Int) = dao.alternarFavorito(id)
 override suspend fun cantidadGuardada() = dao.contar()
 override suspend fun cantidadFavoritos() = dao.contarFavoritos()
 override suspend fun limpiarCache() = db.withTransaction { dao.borrarTodo() }
}
internal suspend fun <T> seguro(block: suspend () -> T): Result<T> = try { Result.success(block()) } catch (e: CancellationException) { throw e } catch (e: Exception) { Result.failure(e) }
