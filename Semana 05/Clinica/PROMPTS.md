# Prompts utilizados con Inteligencia Artificial

## Prompt 1 - Filtro de citas por estado

**Prompt utilizado:**

> Mejora la pantalla MisCitasScreen de mi aplicación Clínica Salud+ desarrollada con Kotlin y Jetpack Compose. Agrega filtros “Todas”, “Confirmadas” y “Completadas” usando estado local con remember y mutableStateOf. Al seleccionar un filtro, la LazyColumn debe mostrar únicamente las citas correspondientes. Mantén el diseño morado actual y no utilices ViewModel ni MVVM.

**Objetivo:**

Mejorar la pantalla Mis citas permitiendo al usuario filtrar las citas según su estado.

**Resultado esperado:**

- Mostrar Todas, Confirmadas y Completadas.
- Permitir una sola selección.
- Actualizar automáticamente la lista.
- Mantener el estado con Jetpack Compose.
- No utilizar ViewModel ni MVVM.

**Validación:**

La mejora fue validada correctamente en el emulador de Android Studio.

Se comprobó que:

- "Todas" muestra todas las citas registradas.
- "Confirmadas" muestra únicamente las citas con estado Confirmada.
- "Completadas" muestra únicamente las citas con estado Completada.
- Al cambiar de filtro, la interfaz se actualiza automáticamente mediante el estado local de Jetpack Compose.