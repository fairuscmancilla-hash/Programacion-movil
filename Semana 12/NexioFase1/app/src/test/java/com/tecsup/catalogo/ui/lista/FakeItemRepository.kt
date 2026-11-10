package com.tecsup.catalogo.ui.lista
import com.tecsup.catalogo.data.repository.ItemRepository
import com.tecsup.catalogo.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/** Repositorio falso: guarda los items en memoria, como si fuera Room. */
class FakeItemRepository(guardados: List<Item> = emptyList()) : ItemRepository {
 private val datos = MutableStateFlow(guardados)
 /** Lo que devolverá la "API" en el próximo refresco. */
 var respuestaApi: Result<List<Item>> = Result.success(emptyList())
 var vecesRefrescado = 0
 val favoritosAlternados = mutableListOf<Int>()

 override val items = datos
 override val favoritos = datos.map { lista -> lista.filter { it.esFavorito } }
 override suspend fun refrescar(): Result<Int> {
  vecesRefrescado++
  return respuestaApi.map { nuevos -> datos.value = nuevos; nuevos.size }
 }
 override suspend fun obtenerDetalle(id: Int) = datos.value.find { it.id == id }?.let { Result.success(it) } ?: Result.failure(NoSuchElementException())
 override suspend fun alternarFavorito(id: Int) {
  favoritosAlternados += id
  datos.value = datos.value.map { if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it }
 }
 override suspend fun cantidadGuardada() = datos.value.size
 override suspend fun cantidadFavoritos() = datos.value.count { it.esFavorito }
 override suspend fun limpiarCache() { datos.value = emptyList() }
}
