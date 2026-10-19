package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AyudaTextField(
    valor: String,
    onCambio: (String) -> Unit,
    modifier: Modifier = Modifier,
    etiqueta: String? = null,
    placeholder: String? = null,
    icono: ImageVector? = null,
    error: String? = null,
    esClave: Boolean = false,
    teclado: KeyboardType = KeyboardType.Text,
    minLineas: Int = 1
) {
    val colores = MaterialTheme.colorScheme
    // Ojo para mostrar u ocultar la contraseña
    var claveVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        modifier = modifier.fillMaxWidth(),
        label = etiqueta?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = icono?.let { { Icon(it, contentDescription = null, tint = colores.primary) } },
        trailingIcon = if (esClave) {
            {
                IconButton(onClick = { claveVisible = !claveVisible }) {
                    Icon(
                        if (claveVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = if (claveVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = colores.onSurfaceVariant
                    )
                }
            }
        } else null,
        isError = error != null,
        supportingText = error?.let { { Text(it) } },
        visualTransformation = if (esClave && !claveVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (esClave) KeyboardType.Password else teclado),
        singleLine = minLineas == 1,
        minLines = minLineas,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = colores.outlineVariant,
            focusedContainerColor = colores.surface,
            unfocusedContainerColor = colores.surface,
            errorContainerColor = colores.surface
        )
    )
}
