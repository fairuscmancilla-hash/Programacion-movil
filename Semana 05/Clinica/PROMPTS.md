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

---

## Prompt 2 - Validación de fecha y hora al agendar una cita

**Prompt utilizado:**

> Mejora la pantalla AgendarCitaScreen de mi aplicación Clínica Salud+ desarrollada con Kotlin y Jetpack Compose. Mantén el uso de estado local con remember y mutableStateOf. El usuario debe seleccionar una sola fecha y una sola hora. El botón para confirmar la cita debe permanecer deshabilitado mientras falte alguna de las dos selecciones y habilitarse automáticamente cuando ambas estén seleccionadas. Muestra también un resumen visual con la fecha y hora seleccionadas. No utilices ViewModel ni MVVM.

**Objetivo:**

Evitar que el usuario continúe con el registro de una cita sin haber seleccionado una fecha y una hora.

**Resultado esperado:**

- Permitir seleccionar una sola fecha.
- Permitir seleccionar una sola hora.
- Mantener el botón deshabilitado si falta una selección.
- Habilitar el botón cuando exista fecha y hora.
- Mostrar un resumen de la selección.
- Mantener el estado utilizando Jetpack Compose.
- No utilizar ViewModel ni MVVM.

**Validación:**

La mejora fue validada correctamente en el emulador de Android Studio.

Se comprobó que:

- El botón permanece deshabilitado mientras no se seleccione una fecha y una hora.
- Solo se puede seleccionar una fecha a la vez.
- Solo se puede seleccionar una hora a la vez.
- El resumen se actualiza automáticamente según la selección.
- Cuando la fecha y la hora están completas, aparece el mensaje "Cita lista para confirmar".
- El botón "Confirmar cita" se habilita únicamente cuando ambas selecciones están completas.