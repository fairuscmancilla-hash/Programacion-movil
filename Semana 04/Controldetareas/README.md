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

## Uso de Gemini en Android Studio

Como parte del laboratorio se utilizó Gemini en Android Studio para generar un desafío técnico relacionado con el manejo de estados en Jetpack Compose.

### Prompt utilizado

Actúa como tech lead de Android.

Necesito que plantees un desafío técnico sobre manejo de estados en Jetpack Compose.

Esto está dirigido a un candidato para una posición de desarrollador Android junior.

Quiero que respondas en formato de prueba técnica, incluyendo contexto del negocio, requerimientos, criterios de evaluación y posibles extensiones.

Ten en cuenta estas condiciones: no incluyas la solución, el problema debe evaluar buenas prácticas y uso correcto de estados.

### Resultado obtenido

Gemini generó una prueba técnica llamada **TaskMaster**, orientada a evaluar:

- Manejo de estados en Jetpack Compose.
- Uso de ViewModel.
- State Hoisting.
- StateFlow o LiveData.
- Flujo de datos unidireccional.
- Filtrado de tareas.
- Persistencia del estado ante cambios de configuración.
- Optimización de recomposiciones.

También propuso extensiones como Swipe para eliminar, Undo mediante Snackbar y animaciones de estado.

## Autor

Yajaira Cerron