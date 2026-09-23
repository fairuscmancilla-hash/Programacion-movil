package com.example.semana05_navegacion.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Paleta del "Portal Académico" (generada con apoyo de IA)
object PortalColors {
    val Primario = Color(0xFF5B4A9E)
    val PrimarioOscuro = Color(0xFF3F3175)
    val Lavanda = Color(0xFFE9E3FF)
    val Rosa = Color(0xFF8E5A7A)
    val Peligro = Color(0xFFC0392B)

    val fondoLogin = Brush.verticalGradient(listOf(Lavanda, Color.White))
    val fondoHome = Brush.verticalGradient(listOf(PrimarioOscuro, Primario, Lavanda, Color.White))
    val cabecera = Brush.verticalGradient(listOf(PrimarioOscuro, Primario))
    val cabeceraPerfil = Brush.horizontalGradient(listOf(Primario, Rosa))
}

// Avatar circular con las iniciales del alumno (sin necesidad de imágenes)
@Composable
fun AvatarIniciales(iniciales: String, size: Dp = 48.dp, conBorde: Boolean = false) {
    Box(
        modifier = Modifier
            .size(size)
            .then(if (conBorde) Modifier.border(4.dp, Color.White, CircleShape) else Modifier)
            .clip(CircleShape)
            .background(Brush.linearGradient(listOf(PortalColors.Primario, PortalColors.Rosa))),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = iniciales,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = (size.value / 2.6f).sp
        )
    }
}

// Foto circular del alumno. Busca la imagen por nombre en res/drawable
// (alumno_1, alumno_2...). Si no la encuentra, muestra las iniciales y la app no se cae.
@Composable
fun FotoAlumno(nombreFoto: String, iniciales: String, size: Dp = 48.dp, conBorde: Boolean = false) {
    val context = LocalContext.current
    val bitmap = remember(nombreFoto) {
        val id = context.resources.getIdentifier(nombreFoto, "drawable", context.packageName)
        if (id == 0) {
            null
        } else {
            // Se reduce la imagen al cargarla para no gastar memoria
            val medidas = BitmapFactory.Options().apply { inJustDecodeBounds = true; inScaled = false }
            BitmapFactory.decodeResource(context.resources, id, medidas)
            var muestra = 1
            while (medidas.outWidth / (muestra * 2) >= 400) muestra *= 2
            val opciones = BitmapFactory.Options().apply { inSampleSize = muestra; inScaled = false }
            BitmapFactory.decodeResource(context.resources, id, opciones)?.asImageBitmap()
        }
    }
    if (bitmap == null) {
        AvatarIniciales(iniciales, size, conBorde)
        return
    }
    Image(
        bitmap = bitmap,
        contentDescription = "Foto del alumno",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .then(if (conBorde) Modifier.border(4.dp, Color.White, CircleShape) else Modifier)
            .clip(CircleShape)
    )
}

// Fila de información con ícono, etiqueta pequeña y valor
@Composable
fun FilaInfo(icono: ImageVector, etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(PortalColors.Lavanda),
            contentAlignment = Alignment.Center
        ) {
            Icon(icono, contentDescription = null, tint = PortalColors.Primario, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(etiqueta, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(valor, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}
