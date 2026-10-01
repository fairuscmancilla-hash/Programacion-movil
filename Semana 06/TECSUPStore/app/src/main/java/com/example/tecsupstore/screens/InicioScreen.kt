package com.example.tecsupstore.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tecsupstore.components.TarjetaProducto
import com.example.tecsupstore.data.productosMock

@Composable
fun InicioScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(productosMock) { producto ->
            TarjetaProducto(
                producto = producto
            )
        }
    }
}