# Prompts utilizados

Fase 2 de AyudaCampus (rama `mejora-ia-semana08`).

> **IA utilizada:** Claude (Claude Code), en lugar de Gemini en Android Studio.
> Los prompts de abajo son los que se usaron para cada mejora, adaptados del ejemplo del laboratorio.

## Prompt 1 — Tiempo promedio de resolución

**Texto enviado a la IA:**

```
Tengo una app Kotlin con Jetpack Compose, Material 3, MVVM, Room y un
AyudaRepository (interface). Agrega en el panel de soporte el tiempo
promedio de resolucion de los tickets cerrados (fechaCierre - fecha).
La consulta va en TicketDao, la funcion en AyudaRepository y su
implementacion, y el valor se expone como StateFlow en PanelViewModel.
La View solo lo muestra. No uses XML. Muestrame solo los archivos
que cambian.
```

**Qué generó la IA:**
- `TicketDao.promedioResolucion()`: `SELECT AVG(fechaCierre - fecha)` de los tickets `CERRADO` con `fechaCierre` no nula, como `Flow<Double?>`.
- `AyudaRepository.observarPromedioResolucion(): Flow<Long?>`, declarada primero en la interface, y su implementación en `AyudaRepositoryRoom`.
- `PanelViewModel.promedioResolucion: StateFlow<Long?>`.
- Una tarjeta en `PanelScreen` ("Tiempo promedio de resolución") y la función `duracion()` en `Formato.kt` para mostrar "6 h 0 min".

**Qué tuve que corregir yo:**
Compiló a la primera; no hubo errores. En la revisión se comprobó que:
- `AVG` en SQLite devuelve un número decimal, así que el DAO devuelve `Double?` y el repository lo convierte a `Long?` (milisegundos) para que el ViewModel no dependa de ese detalle.
- Si no hay tickets cerrados, el promedio es `null` y la pantalla muestra "Sin datos" en vez de "0 min".

**¿Respeta MVVM?:** Sí. La consulta está en el DAO, la función se declaró primero en la interface `AyudaRepository`, el ViewModel solo conoce la interface y la View solo muestra el valor.

## Prompt 2 — Contador de SLA por prioridad

**Texto enviado a la IA:**

```
En la misma app, agrega un contador de SLA: cada ticket ABIERTO o
EN_PROCESO debe mostrar el tiempo que le queda segun su prioridad
(Alta 4 h, Media 24 h, Baja 72 h). El calculo va en el ViewModel
(MisTicketsViewModel y BandejaViewModel) y se actualiza cada minuto.
La View solo lo muestra en la TicketCard. No cambies el DAO ni las
entidades. Muestrame solo los archivos que cambian.
```

**Qué generó la IA:**
- `model/EstadoSla.kt` con el tiempo restante, el límite en horas, `vencido` y `porVencer` (menos del 25 % del tiempo).
- `viewmodel/CalculoSla.kt` con la regla del SLA y un `reloj()` que emite la hora cada minuto.
- `sla: StateFlow<Map<Int, EstadoSla>>` en `MisTicketsViewModel` y `BandejaViewModel` (combina los tickets con el reloj).
- `SlaChip` en `TicketCard`: verde a tiempo, ámbar por vencer, rojo vencido.

**Qué tuve que corregir yo:**
Compiló a la primera; no hubo errores. En la revisión se comprobó que:
- Se usó `horasRespuesta` que ya existía en el enum `Prioridad` (4, 24 y 72 h) en lugar de repetir los números en otro lugar.
- Los tickets `RESUELTO` y `CERRADO` no muestran SLA, porque el plazo ya terminó.

**¿Respeta MVVM?:** Sí. No se tocaron el DAO, las entidades ni el Repository. La regla vive en la capa ViewModel y la `TicketCard` solo recibe el `EstadoSla` ya calculado.

## Prompt 3 — Búsqueda avanzada en la bandeja

**Texto enviado a la IA:**

```
En la bandeja de Soporte, permite filtrar por prioridad, categoria y
texto (titulo, numero o nombre del cliente) al mismo tiempo, ademas
del chip de estado que ya existe. Los filtros y el filtrado van en
BandejaViewModel con combine; la View solo muestra los chips, el
buscador y un boton "Limpiar filtros". Conserva el orden por
prioridad que entrega el DAO. Muestrame solo los archivos que cambian.
```

**Qué generó la IA:**
- En `BandejaViewModel`: los filtros `prioridad`, `categoriaId` y `texto`, la lista `categorias`, `hayFiltros` y `limpiarFiltros()`. `tickets` combina los 5 flujos (tickets, estado, prioridad, categoría y texto) con `combine`.
- En `BandejaScreen`: el buscador, los chips de prioridad (con su color), los chips de categoría (con su ícono), el contador "N ticket(s)" y "Limpiar filtros".

**Qué tuve que corregir yo:**
- En modo oscuro, el texto de los chips elegidos era blanco fijo y se leía mal sobre el violeta claro. Se cambió a `onPrimary` del tema para los chips sin color propio.
- La lista de categorías del filtro incluye también las inactivas, porque pueden tener tickets antiguos.

**¿Respeta MVVM?:** Sí. Solo cambiaron el ViewModel y la View, como indica la tabla del laboratorio. La View no filtra nada: solo llama a `filtrarPrioridad`, `filtrarCategoria`, `buscar` y `limpiarFiltros`.

## Reflexión (5 líneas)

- **¿Qué mejoró la IA y qué no?** Generó rápido el código repetitivo (StateFlows, `combine`, chips), pero los detalles de diseño y los casos límite hubo que revisarlos.
- **¿Qué error cometió?** Un color fijo que no funcionaba en modo oscuro; también hay que vigilar que no ponga cálculos dentro de los Composables.
- **¿Qué aprendí sobre MVVM?** Si cada capa tiene su responsabilidad, una mejora solo toca las capas necesarias (el SLA no tocó Room).
- **¿Qué aprendí sobre Room?** Las consultas agregadas (`AVG`, `COUNT`) pueden devolver `Flow` y actualizar el panel solas.
- **¿Qué aprendí sobre las reglas de negocio?** En el ViewModel se pueden probar y reutilizar (`CalculoSla` lo usan dos pantallas).
