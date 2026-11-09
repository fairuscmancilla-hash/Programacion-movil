package com.tecsup.catalogo.ui.components
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.*
import com.tecsup.catalogo.R
/** Recorta solo la visualización del archivo original, sin modificar el logo. */
@Composable fun NexioLogo(modifier: Modifier = Modifier) {
 val bitmap=ImageBitmap.imageResource(R.drawable.nexio_logo)
 Canvas(modifier.graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }) { drawImage(bitmap,srcOffset=IntOffset((bitmap.width*.175f).toInt(),(bitmap.height*.245f).toInt()),srcSize=IntSize((bitmap.width*.65f).toInt(),(bitmap.height*.48f).toInt()),dstSize=IntSize(size.width.toInt(),size.height.toInt())); drawRect(Brush.horizontalGradient(0f to Color.Transparent,.035f to Color.White,.965f to Color.White,1f to Color.Transparent),blendMode=BlendMode.DstIn); drawRect(Brush.verticalGradient(0f to Color.Transparent,.035f to Color.White,.965f to Color.White,1f to Color.Transparent),blendMode=BlendMode.DstIn) }
}
