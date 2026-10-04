package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Tarjeta de producto usada en el grid de Inicio.
 * Muestra la imagen real del producto, favoritos
 * y permite agregarlo al carrito.
 */
@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    onAgregar: () -> Unit,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            // -----------------------------
            // IMAGEN DEL PRODUCTO
            // -----------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.15f)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {

                Image(
                    painter = painterResource(
                        id = producto.imagenRes
                    ),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )

                // -----------------------------
                // FAVORITO
                // -----------------------------
                IconButton(
                    onClick = onFavoritoClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(
                                alpha = 0.85f
                            ),
                            shape = CircleShape
                        )
                ) {

                    Icon(
                        imageVector = if (esFavorito) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = if (esFavorito) {
                            "Quitar de favoritos"
                        } else {
                            "Agregar a favoritos"
                        },
                        tint = if (esFavorito) {
                            Color.Red
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // -----------------------------
            // NOMBRE
            // -----------------------------
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            // -----------------------------
            // PRECIO + AGREGAR
            // -----------------------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "S/ %.2f".format(producto.precio),
                    style = MaterialTheme.typography.labelMedium,
                    color = VerdeBodega,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onAgregar,
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = VerdeBodega,
                            shape = CircleShape
                        )
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar ${producto.nombre}",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
