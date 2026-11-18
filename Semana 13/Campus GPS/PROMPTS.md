# PROMPTS — Fase 2 de CampusGPS

Mejoras realizadas con asistencia de IA en la rama `mejora-ia-semana13`. La herramienta utilizada fue **Claude (Claude Code)**.

| Prompt que escribí | Qué respondió la IA | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «ahora puedes generar la fase con ia» | Revisó las mejoras de la guía y propuso tres: filtro por categoría, radio de cercanía y último lugar visitado. | Se descartaron el punto azul nativo, el seguimiento en vivo y el mapa en modo oscuro. | Las tres elegidas cambian el ViewModel o el Repository y se pueden comprobar en el emulador. El punto azul quedaría oculto bajo el marcador rojo de la posición. |
| Mismo prompt · Mejora 1: filtrar por categoría | Agregó chips de categoría y una lista filtrada con `combine` en `MapaViewModel`. | El filtro se dejó en `util/Filtros.kt`. El lugar seleccionado se limpia si el filtro lo oculta. La lista bajó de 220 dp a 170 dp. | La vista solo dibuja el estado. Una selección oculta dejaría la cámara en un lugar que no aparece en la lista, y la altura menor mantiene visible el mapa. |
| Mismo prompt · Mejora 2: radio de cercanía | Agregó el selector de 100, 200 y 500 m, un `Circle` alrededor de la posición y una marca en la lista. | El cálculo de `dentroDelRadio` se hizo en `util/Filtros.kt` y lo aplica el ViewModel. La marca se muestra con el texto «Dentro del radio». | Mantiene la lógica fuera de los Composables, como exige MVVM. Usar texto evita depender solo del color para entender la marca. |
| Mismo prompt · Mejora 3: último lugar visitado | Agregó `UltimoLugarRepository` y su implementación con DataStore. El ViewModel guarda la selección y la recupera al abrir la app. | El ViewModel recibe la interface y no un `Context`. Los errores de lectura `IOException` devuelven preferencias vacías. | Respeta la regla de que el ViewModel no conozca Android y permite probarlo con un repositorio falso. Si falla la lectura, la app sigue abierta. |

## Verificación

- Compilación correcta en cada mejora.
- 7 pruebas unitarias aprobadas: 3 de la Fase 1 y 4 de la Fase 2.
- Pruebas en el emulador: filtro, cambio de radio, círculo en el mapa, selección y recuperación del último lugar después de cerrar la app, y rotación de pantalla.
