package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.theme.Violeta

/** Mensajes propios a la derecha (color del rol); los del otro perfil a la izquierda. */
@Composable
fun BurbujaComentario(comentario: Comentario, esPropio: Boolean, modifier: Modifier = Modifier) {
    val colores = MaterialTheme.colorScheme
    val fondo = when {
        esPropio -> colores.primary
        comentario.autorRol == Rol.ADMIN.name -> Violeta.copy(alpha = 0.14f)
        else -> colores.surfaceVariant
    }
    val tinta = if (esPropio) colores.onPrimary else colores.onSurface
    val forma = if (esPropio) RoundedCornerShape(16.dp, 4.dp, 16.dp, 16.dp)
    else RoundedCornerShape(4.dp, 16.dp, 16.dp, 16.dp)

    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = if (esPropio) Arrangement.End else Arrangement.Start
    ) {
        Surface(color = fondo, shape = forma, modifier = Modifier.widthIn(max = 270.dp)) {
            Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                // Mockup: "Soporte TI · 10:20" y "Juan P. · 10:32"
                val autor = when {
                    esPropio -> "Tú"
                    comentario.autorRol == Rol.ADMIN.name -> comentario.autorNombre
                    else -> nombreCorto(comentario.autorNombre)
                }
                Text(
                    "$autor · ${momento(comentario.fecha)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = tinta.copy(alpha = 0.75f)
                )
                Text(comentario.texto, style = MaterialTheme.typography.bodyMedium, color = tinta)
            }
        }
    }
}
