# Laboratorio 04 - Control de Tareas

## Descripción

Aplicación desarrollada en Kotlin con Jetpack Compose para practicar el manejo de estados en Android.

La aplicación permite registrar tareas, visualizarlas, marcarlas como completadas y eliminarlas.

## Funcionalidades

- Agregar nuevas tareas.
- Mostrar el total de tareas registradas.
- Marcar una tarea como completada.
- Desmarcar una tarea.
- Eliminar tareas.
- Actualización automática de la interfaz mediante estados.

## Conceptos utilizados

- Jetpack Compose
- `remember`
- `mutableStateOf`
- `mutableStateListOf`
- Recomposición
- `Checkbox`
- Eventos con `onClick`
- Material Design 3

## Evidencias

### Registro de tareas
Se ingresan tareas desde un campo de texto y se agregan a la lista.

### Contador de tareas
La aplicación muestra automáticamente el total de tareas registradas.

### Tareas completadas
Cada tarea puede marcarse mediante un `Checkbox`.

### Eliminación de tareas
Las tareas pueden eliminarse y el contador se actualiza automáticamente.

## Autor

Yajaira Cerron