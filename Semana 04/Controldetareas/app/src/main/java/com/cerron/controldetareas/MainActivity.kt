package com.cerron.controldetareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Checkbox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PantallaTareas()
            }
        }
    }
}

data class Tarea(
    val id: Int,
    val texto: String,
    val completada: Boolean = false
)

@Composable
fun PantallaTareas() {

    var nuevaTarea by remember {
        mutableStateOf("")
    }

    val listaTareas = remember {
        mutableStateListOf<Tarea>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Lista de tareas - Tecsup",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nuevaTarea,
            onValueChange = {
                nuevaTarea = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Ingrese una tarea")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (nuevaTarea.isNotBlank()) {

                    listaTareas.add(
                        Tarea(
                            id = listaTareas.size + 1,
                            texto = nuevaTarea
                        )
                    )

                    nuevaTarea = ""
                }
            }
        ) {
            Text("Agregar tarea")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Total de tareas: ${listaTareas.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        listaTareas.forEach { tarea ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = tarea.completada,
                    onCheckedChange = { completada ->

                        val indice = listaTareas.indexOf(tarea)

                        if (indice != -1) {
                            listaTareas[indice] =
                                listaTareas[indice].copy(
                                    completada = completada
                                )
                        }
                    }
                )

                Text(
                    text = tarea.texto,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        listaTareas.remove(tarea)
                    }
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}