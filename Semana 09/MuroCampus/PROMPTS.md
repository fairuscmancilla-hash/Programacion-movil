# PROMPTS.md · Fase 2 (con IA)

**Rama:** `mejora-ia-semana09` (la rama `mejora-ia` ya existía en el repositorio con el laboratorio 03, por eso se usa el
mismo formato de nombre que en las semanas anteriores).

**Herramienta de IA usada:** Claude (Claude Code). La guía menciona Gemini en Android Studio; el trabajo con IA se hizo
con Claude, siguiendo la misma regla: *la IA propone, yo decido*. Todo lo generado se leyó, se probó en el emulador y se
corrigió cuando rompía las reglas del laboratorio.

## Mejoras elegidas

| Mejora | Capas que cambian | Commit |
|---|---|---|
| Borrar publicación con `@DELETE` y `Response<Unit>` | API + Repository + ViewModel + View | `feat(eliminar): borra publicaciones con DELETE y Response<Unit>` |
| Editar el título con `@PATCH` | Modelo + API + Repository + ViewModel + View | `feat(editar): cambia el titulo de una publicacion con PATCH` |
| Búsqueda por título | ViewModel + View | `feat(busqueda): filtra por titulo las publicaciones descargadas` |

Dos de las tres mejoras cambian la interface de la API y el Repository, como piden las reglas de la Fase 2.

## Prompts importantes

| Prompt que escribí | Qué respondió la IA | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «Haz la Fase 2 del laboratorio en una rama nueva `mejora-ia`» | Propuso crear la rama `mejora-ia` desde `main` | Revisé `origin/mejora-ia` antes de crearla: ya existía y tenía el laboratorio 03. Usé `mejora-ia-semana09` | Hacer push a `mejora-ia` habría mezclado este laboratorio con otro. Mis semanas anteriores ya usan `mejora-ia-semanaXX` |
| «Agrega un botón Eliminar en el detalle con `@DELETE("posts/{id}")` que revise el código de respuesta con `Response<Unit>`, sin romper MVVM + Repository» | `eliminar()` en `PostApi` devolviendo `Response<Unit>`; en el Repository, si `!isSuccessful` lanza `HttpException(respuesta)` dentro de `llamar { }`; en el ViewModel un `StateFlow<UiState<Int>?>`; en la vista un icono de papelera y un diálogo de confirmación | Mantuve la revisión del código **en el Repository**, nunca en el ViewModel. Agregué que, al tener éxito, el ViewModel quite la publicación de la lista ya descargada. El diálogo salía lila (color por defecto de Material 3) y lo cambié a blanco | Con `Response<Unit>` Retrofit no lanza la excepción solo: si no se revisa `isSuccessful`, un 404 parecería éxito. `HttpException` en el ViewModel está prohibido. JSONPlaceholder responde 200 pero no borra nada, así que sin el cambio local la publicación seguiría en la lista. El lila no es de la paleta del modelo |
| «Agrega una pantalla para modificar el título con `@PATCH` y muestra la respuesta del servidor» | `data class CambioTitulo(title)` como `@Body`, `editarTitulo()` en API y Repository, estado `edicion` en el ViewModel y `PantallaEditar` con el campo y el botón Guardar | El botón Guardar solo se habilita si el título cambió y no está vacío. El detalle y la lista se actualizan con **la respuesta del servidor**, no con el texto escrito. Al guardar se cierra el teclado | Enviar un PATCH sin cambios es una petición inútil. Si el servidor cambia o rechaza el dato, la app debe mostrar lo que el servidor confirmó. En el emulador, el teclado tapaba el recuadro "Respuesta del servidor" |
| «Agrega un campo de búsqueda que filtre por título las publicaciones ya descargadas» | `busqueda` como `MutableStateFlow<String>` y `listaVisible` con `combine(lista, busqueda).stateIn(...)`; en la vista un `OutlinedTextField` con lupa | Comprobé en Logcat que buscar **no** hace peticiones (solo hay un `GET posts`). Agregué la X para borrar, el mensaje "Sin resultados para …" distinto de "Sin publicaciones" y `trim()` al texto | La guía pide filtrar lo ya descargado: llamar a la API en cada letra sería lento y gastaría datos. El usuario debe distinguir "no hay datos" de "tu búsqueda no coincide". Un espacio al final no debe vaciar la lista |

## Revisión del código generado (sustentación)

| Regla | Cómo se comprobó | Resultado |
|---|---|---|
| Nada de Retrofit, `PostApi` ni `HttpException` en el ViewModel | `grep -ri "retrofit\|HttpException\|PostApi" viewmodel/` después de cada mejora | Sin coincidencias. `HttpException` solo aparece en `data/PostRepository.kt` y `data/ErrorApp.kt` |
| Ningún `catch` vacío que trague excepciones | Revisión de `llamar { }` | Relanza `CancellationException` y convierte el resto en `Result.failure(ErrorApp)` |
| La pantalla nunca llama a la API | Revisión de `ui/` | Las pantallas solo llaman funciones del ViewModel (`eliminar`, `editarTitulo`, `buscar`) |
| Sin `GlobalScope` | Búsqueda en el proyecto | Todo usa `viewModelScope` |
| URL con `https://` | `RetrofitClient.URL_BASE` | `https://jsonplaceholder.typicode.com/` |

## Pruebas en el emulador

| Prueba | Resultado |
|---|---|
| Eliminar la publicación #1 | `DELETE posts/1` → 200, vuelve a la lista y la #1 ya no aparece |
| Eliminar sin internet | "No se pudo eliminar: Sin conexión a internet…" y se queda en el detalle |
| Editar el título de la #1 | `PATCH posts/1` con `{"title":"Bienvenidos al ciclo 2026"}` → 200; el título nuevo se ve en Editar, Detalle y Lista |
| Buscar "dolorem" | Muestra #6, #8, #9, #12… (las 8 que coinciden en la API) sin nuevas peticiones |
| Buscar texto inexistente | "Sin resultados para “…”" |
| Búsqueda + filtro Usuario 1 | Solo #6, #8 y #9 |
