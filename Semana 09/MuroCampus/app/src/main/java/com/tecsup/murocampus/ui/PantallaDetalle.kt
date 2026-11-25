package com.tecsup.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.Post
import com.tecsup.murocampus.viewmodel.DetalleUi
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

/** Paso 7: publicacion completa y sus comentarios. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    id: Int,
    vm: PostsViewModel,
    onVolver: () -> Unit,
    onEditar: () -> Unit,
    onEliminado: () -> Unit
) {
    val estado by vm.detalle.collectAsStateWithLifecycle()
    val eliminacion by vm.eliminacion.collectAsStateWithLifecycle()
    var confirmar by rememberSaveable { mutableStateOf(false) }

    // Mejora 1: si el servidor confirmo el DELETE se vuelve a la lista
    LaunchedEffect(eliminacion) {
        if (eliminacion is UiState.Exito) onEliminado()
    }

    val eliminando = eliminacion is UiState.Cargando

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraConVolver(titulo = "Detalle", onVolver = onVolver) {
                // Solo se puede borrar cuando la publicacion ya cargo
                if (estado is UiState.Exito) {
                    IconButton(onClick = onEditar, enabled = !eliminando) {
                        Icon(Icons.Filled.Edit, contentDescription = "Editar título")
                    }
                    if (eliminando) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(end = 16.dp).size(22.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        IconButton(onClick = { confirmar = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar publicación")
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            (eliminacion as? UiState.Error)?.let { e ->
                MensajeEstado(
                    texto = "No se pudo eliminar: ${e.mensaje}",
                    icono = Icons.Filled.Warning,
                    fondo = MaterialTheme.colorScheme.errorContainer,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
                )
            }
            when (val e = estado) {
                is UiState.Cargando -> Cargando("Cargando publicación...")
                is UiState.Error -> ErrorConReintento(e.mensaje, onReintentar = { vm.abrir(id) })
                is UiState.Exito -> ContenidoDetalle(e.datos)
            }
        }
    }

    if (confirmar) {
        AlertDialog(
            onDismissRequest = { confirmar = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text("¿Eliminar publicación #$id?") },
            text = { Text("Se enviará DELETE posts/$id al servidor.") },
            confirmButton = {
                TextButton(onClick = { confirmar = false; vm.eliminar(id) }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmar = false }) { Text("Cancelar") }
            }
        )
    }
}

/** Barra superior azul con flecha de regreso y acciones opcionales a la derecha. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraConVolver(titulo: String, onVolver: () -> Unit, acciones: @Composable RowScope.() -> Unit = {}) {
    TopAppBar(
        title = { Text(titulo, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            IconButton(onClick = onVolver) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
        },
        actions = acciones,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun ContenidoDetalle(datos: DetalleUi) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { TarjetaDetalle(datos.post) }
        item {
            Text(
                text = "Comentarios (${datos.comentarios.size})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
            )
        }
        items(datos.comentarios, key = { it.id }) { TarjetaComentario(it) }
    }
}

@Composable
private fun TarjetaDetalle(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            EncabezadoAutor(nombre = "Usuario ${post.userId}", secundario = "Publicación #${post.id}")
            Spacer(Modifier.height(12.dp))
            // Titulo y texto completos (sin maxLines)
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun TarjetaComentario(comentario: Comentario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row {
                Avatar(tamano = 30.dp)
                Spacer(Modifier.width(10.dp))
                Column {
                    // Correo en azul (color primario del tema)
                    Text(
                        text = comentario.email,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = comentario.body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
