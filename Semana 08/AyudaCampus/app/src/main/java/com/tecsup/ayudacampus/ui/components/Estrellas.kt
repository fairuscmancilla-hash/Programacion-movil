package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.theme.Estrella

/** Calificación de 1 a 5. Si onCambio es null, solo se muestra. */
@Composable
fun Estrellas(
    valor: Int,
    modifier: Modifier = Modifier,
    onCambio: ((Int) -> Unit)? = null,
    tamano: Dp = 36.dp
) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        (1..5).forEach { n ->
            Icon(
                Icons.Filled.Star,
                contentDescription = "$n estrellas",
                tint = if (n <= valor) Estrella else MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier
                    .size(tamano)
                    .then(if (onCambio != null) Modifier.clickable { onCambio(n) } else Modifier)
            )
        }
    }
}
