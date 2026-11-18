# PROMPTS · Fase 2

**Herramienta usada:** Claude Code. La guía propone Gemini, pero las mejoras de esta fase se hicieron con Claude Code, por eso la tercera columna indica lo que respondió esta herramienta.

**Rama:** `mejora-ia-semana14`

| Prompt que escribí | Qué respondió Claude Code | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| «todo bien ahora la fs» y «fase con ia» | Propuso tres de las seis mejoras de la guía y creó la rama `mejora-ia-semana14` | Se descartaron Tiempo máximo, Reintento automático e Historial de resultados. La rama no se llamó `mejora-ia` | La guía pide mínimo 3 mejoras. Con un límite de 2 s, la carga de pedidos, que tarda 3 s, fallaría siempre. La rama `mejora-ia` ya existe en el repositorio con otro laboratorio |
| «todo bien ahora la fs» (mejora 1: progreso desde el Repository) | Movió la tarea simulada a `tareaConProgreso()`, que emite el avance con `flow` y `flowOn(Dispatchers.Default)`. El ViewModel solo recoge los valores | Se quitó del ViewModel el ciclo con `delay` y `withContext`. El log `Dentro` pasó al Repository | El trabajo pesado debe estar en el Repository, y Logcat debe seguir mostrando el hilo de cada etapa |
| «todo bien ahora la fs» (mejora 2: primos con progreso) | Reemplazó `contarPrimos` por `contarPrimosConAvance()`, agregó `AvancePrimos` y mostró una barra con el porcentaje | `PrimosUi.Calculando` pasó a guardar el porcentaje. Solo se emite cuando el porcentaje cambia | Emitir por cada número saturaría la pantalla. Contar hasta 3 000 000 subió de unos 3,3 s a entre 4,3 y 5,7 s |
| «todo bien ahora la fs» (mejora 3: comparación de tiempos) | Agregó una tabla con veces, último tiempo, promedio y ahorro de cada modo, y un botón Limpiar | El título de la tabla cambió de «Comparación de tiempos» a «Tiempos acumulados». Las cargas con error no se registran | Ese título repetía el subtítulo de la tarjeta. Una carga fallida no representa un tiempo real de carga |
| «Registrar Claude Code» | Creó este archivo con los prompts reales de la fase | No se registraron respuestas de Gemini | Gemini no se usó en estas mejoras |

## Comprobación

Cada mejora se probó en el emulador Pixel_5 antes de guardarla. El código no usa `GlobalScope`, `runBlocking` ni `Thread.sleep`, y la `CancellationException` se vuelve a lanzar.
