package com.tecsup.murocampus.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.model.Post
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

/** Mejora 2: cambia el titulo de una publicacion con PATCH y muestra la respuesta. */
@Composable
fun PantallaEditar(vm: PostsViewModel, onVolver: () -> Unit) {
    val detalle by vm.detalle.collectAsStateWithLifecycle()
    val edicion by vm.edicion.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { BarraConVolver(titulo = "Editar título", onVolver = onVolver) }
    ) { padding ->
        // Se edita la publicacion que esta abierta en el detalle
        val post = (detalle as? UiState.Exito)?.datos?.post
        if (post == null) {
            Cargando("Cargando publicación...", Modifier.padding(padding))
        } else {
            FormularioEditar(
                post = post,
                edicion = edicion,
                onGuardar = { vm.editarTitulo(post.id, it) },
                onEscribir = { if (edicion != null && edicion !is UiState.Cargando) vm.reiniciarEdicion() },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun FormularioEditar(
    post: Post,
    edicion: UiState<Post>?,
    onGuardar: (String) -> Unit,
    onEscribir: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Se recuerda por id: si cambia la publicacion, el campo se vuelve a llenar
    var titulo by rememberSaveable(post.id) { mutableStateOf(post.title) }
    val foco = LocalFocusManager.current
    val enviando = edicion is UiState.Cargando
    val puedeGuardar = titulo.isNotBlank() && titulo.trim() != post.title && !enviando

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Publicación #${post.id}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Título",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it; onEscribir() },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { foco.clearFocus(); onGuardar(titulo) },   // cierra el teclado para ver la respuesta
            enabled = puedeGuardar,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            if (enviando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            } else {
                Text("Guardar cambios", style = MaterialTheme.typography.titleMedium)
            }
        }

        Spacer(Modifier.height(20.dp))

        when (edicion) {
            is UiState.Exito -> {
                MensajeEstado(
                    texto = "Título actualizado",
                    icono = Icons.Filled.CheckCircle,
                    fondo = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                RespuestaServidor(edicion.datos)
            }
            is UiState.Error -> MensajeEstado(
                texto = edicion.mensaje,
                icono = Icons.Filled.Warning,
                fondo = MaterialTheme.colorScheme.errorContainer,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            UiState.Cargando, null -> Unit
        }
    }
}

/** Lo que devolvio el servidor despues del PATCH. */
@Composable
private fun RespuestaServidor(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Respuesta del servidor",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text("id: ${post.id}   ·   userId: ${post.userId}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text("title: ${post.title}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
