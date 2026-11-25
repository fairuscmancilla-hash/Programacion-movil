package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Paso 7: lo que necesita la pantalla de detalle. */
data class DetalleUi(
    val post: Post,
    val comentarios: List<Comentario>
)

/**
 * ViewModel de MuroCampus. Solo conoce la interface PostRepository:
 * no sabe nada de la red ni de Android; recibe Result y lo convierte en UiState.
 */
class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    // ---------- Paso 6: lista ----------
    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista: StateFlow<UiState<List<Post>>> = _lista.asStateFlow()

    // ---------- Paso 8: filtro (null = Todos) ----------
    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro: StateFlow<Int?> = _usuarioFiltro.asStateFlow()

    // ---------- Paso 7: detalle ----------
    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    // ---------- Paso 9: crear (null = aun no se envio) ----------
    private val _creacion = MutableStateFlow<UiState<Post>?>(null)
    val creacion: StateFlow<UiState<Post>?> = _creacion.asStateFlow()

    // ---------- Mejora 1: eliminar (Exito lleva el id borrado; null = nada pendiente) ----------
    private val _eliminacion = MutableStateFlow<UiState<Int>?>(null)
    val eliminacion: StateFlow<UiState<Int>?> = _eliminacion.asStateFlow()

    // ---------- Mejora 2: editar titulo (Exito lleva la respuesta del servidor) ----------
    private val _edicion = MutableStateFlow<UiState<Post>?>(null)
    val edicion: StateFlow<UiState<Post>?> = _edicion.asStateFlow()

    // Trabajos en curso: si llega una nueva peticion se cancela la anterior
    private var trabajoLista: Job? = null
    private var trabajoDetalle: Job? = null

    init {
        cargar()
    }

    /** Paso 6: Cargando -> repo -> Exito / Error. Respeta el filtro activo. */
    fun cargar() {
        trabajoLista?.cancel()
        trabajoLista = viewModelScope.launch {
            _lista.value = UiState.Cargando
            val userId = _usuarioFiltro.value
            val resultado = if (userId == null) repo.listar() else repo.porUsuario(userId)
            resultado
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    /** Paso 8: guarda el filtro y recarga (null -> listar(), si no -> porUsuario()). */
    fun filtrar(userId: Int?) {
        if (userId == _usuarioFiltro.value && _lista.value is UiState.Exito) return
        _usuarioFiltro.value = userId
        cargar()
    }

    /** Paso 7: pide la publicacion y sus comentarios a la vez; si una falla, todo es Error. */
    fun abrir(id: Int) {
        trabajoDetalle?.cancel()
        trabajoDetalle = viewModelScope.launch {
            _detalle.value = UiState.Cargando
            val (resPost, resComentarios) = coroutineScope {
                val post = async { repo.detalle(id) }
                val comentarios = async { repo.comentarios(id) }
                post.await() to comentarios.await()
            }
            val error = resPost.exceptionOrNull() ?: resComentarios.exceptionOrNull()
            _detalle.value = if (error != null) {
                UiState.Error(mensajeDe(error))
            } else {
                UiState.Exito(DetalleUi(resPost.getOrThrow(), resComentarios.getOrThrow()))
            }
        }
    }

    /** Paso 9: envia un NuevoPost (userId = 1) y guarda la respuesta. */
    fun publicar(titulo: String, texto: String) {
        if (titulo.isBlank() || texto.isBlank()) return
        if (_creacion.value is UiState.Cargando) return
        viewModelScope.launch {
            _creacion.value = UiState.Cargando
            repo.crear(NuevoPost(userId = 1, title = titulo.trim(), body = texto.trim()))
                .onSuccess { _creacion.value = UiState.Exito(it) }
                .onFailure { _creacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    /** Al salir de la pantalla Crear se olvida el ultimo resultado. */
    fun reiniciarCreacion() {
        _creacion.value = null
    }

    /**
     * Mejora 1: pide DELETE posts/{id}. JSONPlaceholder responde 200 pero no borra de verdad,
     * asi que al tener exito se quita la publicacion de la lista ya descargada.
     */
    fun eliminar(id: Int) {
        if (_eliminacion.value is UiState.Cargando) return
        viewModelScope.launch {
            _eliminacion.value = UiState.Cargando
            repo.eliminar(id)
                .onSuccess {
                    val actual = _lista.value
                    if (actual is UiState.Exito) {
                        _lista.value = UiState.Exito(actual.datos.filterNot { it.id == id })
                    }
                    _eliminacion.value = UiState.Exito(id)
                }
                .onFailure { _eliminacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    /** Al salir del detalle se olvida el resultado de la eliminacion. */
    fun reiniciarEliminacion() {
        _eliminacion.value = null
    }

    /**
     * Mejora 2: envia PATCH posts/{id} con el titulo nuevo. Con la respuesta se actualizan
     * el detalle abierto y la lista ya descargada, sin volver a pedirlos.
     */
    fun editarTitulo(id: Int, titulo: String) {
        val limpio = titulo.trim()
        if (limpio.isEmpty() || _edicion.value is UiState.Cargando) return
        viewModelScope.launch {
            _edicion.value = UiState.Cargando
            repo.editarTitulo(id, limpio)
                .onSuccess { post ->
                    val detalleActual = _detalle.value
                    if (detalleActual is UiState.Exito && detalleActual.datos.post.id == id) {
                        _detalle.value = UiState.Exito(detalleActual.datos.copy(post = post))
                    }
                    val listaActual = _lista.value
                    if (listaActual is UiState.Exito) {
                        _lista.value = UiState.Exito(listaActual.datos.map { if (it.id == id) post else it })
                    }
                    _edicion.value = UiState.Exito(post)
                }
                .onFailure { _edicion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    /** Al salir de la pantalla Editar se olvida el ultimo resultado. */
    fun reiniciarEdicion() {
        _edicion.value = null
    }
}
