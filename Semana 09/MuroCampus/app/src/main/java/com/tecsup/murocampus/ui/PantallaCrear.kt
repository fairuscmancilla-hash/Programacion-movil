package com.tecsup.murocampus.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

/** Paso 9: formulario para crear una publicacion con POST. */
@Composable
fun PantallaCrear(vm: PostsViewModel, onVolver: () -> Unit) {
    val creacion by vm.creacion.collectAsStateWithLifecycle()

    var titulo by rememberSaveable { mutableStateOf("") }
    var contenido by rememberSaveable { mutableStateOf("") }

    // Exito: se limpia el formulario. Error: se conserva lo escrito para reintentar.
    LaunchedEffect(creacion) {
        if (creacion is UiState.Exito) {
            titulo = ""
            contenido = ""
        }
    }

    // Si ya se mostro un exito y el usuario empieza otra publicacion, se oculta el mensaje
    val alEscribir = { if (creacion is UiState.Exito) vm.reiniciarCreacion() }

    val enviando = creacion is UiState.Cargando
    val puedePublicar = titulo.isNotBlank() && contenido.isNotBlank() && !enviando

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { BarraConVolver(titulo = "Nueva publicación", onVolver = onVolver) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Etiqueta("Título")
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it; alEscribir() },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = coloresCampo()
            )

            Spacer(Modifier.height(16.dp))

            Etiqueta("Contenido")
            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it; alEscribir() },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                shape = RoundedCornerShape(10.dp),
                colors = coloresCampo()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { vm.publicar(titulo, contenido) },
                enabled = puedePublicar,
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
                    Text("Publicar", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(Modifier.height(20.dp))

            when (val e = creacion) {
                is UiState.Exito -> Mensaje(
                    texto = "Publicado con id ${e.datos.id}",
                    icono = Icons.Filled.CheckCircle,
                    fondo = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.primary
                )
                is UiState.Error -> Mensaje(
                    texto = e.mensaje,
                    icono = Icons.Filled.Warning,
                    fondo = MaterialTheme.colorScheme.errorContainer,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                UiState.Cargando, null -> Unit
            }
        }
    }
}

@Composable
private fun Etiqueta(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    cursorColor = MaterialTheme.colorScheme.primary
)

@Composable
private fun Mensaje(texto: String, icono: ImageVector, fondo: Color, color: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = fondo
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icono, contentDescription = null, tint = color)
            Spacer(Modifier.width(12.dp))
            Text(texto, color = color, style = MaterialTheme.typography.labelLarge)
        }
    }
}
