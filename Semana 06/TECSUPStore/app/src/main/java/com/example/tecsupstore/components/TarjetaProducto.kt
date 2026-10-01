package com.example.tecsupstore.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tecsupstore.model.Producto

@Composable
fun TarjetaProducto(
    producto: Producto
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = producto.categoria,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "S/ ${producto.precio}",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Box {
                IconButton(
                    onClick = {
                        expanded = true
                    }
                ) {
                    Text(
                        text = "⋮",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}