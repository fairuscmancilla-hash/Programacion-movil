# PROMPTS.md · Fase 2 (con IA)

**Rama:** `mejora-ia-semana09` 
**Herramienta de IA usada:** 

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
| «Tengo una app Android llamada MuroCampus en Kotlin + Jetpack Compose que consume JSONPlaceholder con Retrofit 2.11, OkHttp 4.12 y kotlinx.serialization. La arquitectura es MVVM + Repository: `PostApi` → `PostRepository` (devuelve `Result` y traduce las excepciones a `ErrorApp`) → `PostsViewModel` (expone `StateFlow<UiState>`) → pantallas Compose. Quiero agregar tres mejoras: eliminar una publicación, editar su título con PATCH y buscar por título. Restricciones: el ViewModel no puede importar Retrofit, `PostApi` ni `HttpException`; las pantallas nunca llaman a la API; nada de `GlobalScope` ni `catch` vacíos. Propón qué archivos cambian en cada capa y en qué orden implementarlas.» | Un plan por capas para cada mejora: funciones nuevas en `PostApi` y `PostRepository`, nuevos `StateFlow` en el ViewModel, cambios en `PantallaDetalle` y `PantallaLista` y una pantalla nueva para editar | Implementé primero Eliminar y después Editar, porque cambian la API y el Repository (lo que exige la regla de la Fase 2), y dejé Búsqueda al final. Hice un commit por mejora y, entre una y otra, compilé y probé en el emulador | Si una mejora rompía algo, era fácil saber cuál fue. Además, cada commit queda con un mensaje que dice exactamente qué mejora contiene |
| «Implementa la mejora Eliminar. En `PostApi` agrega `@DELETE("posts/{id}")` que devuelva `Response<Unit>`. En `PostRepositoryRemote` implementa `eliminar(id): Result<Unit>` reutilizando el helper `llamar { }`, revisando el código HTTP de la respuesta. En `PostsViewModel` expón un `StateFlow<UiState<Int>?>` llamado `eliminacion` y la función `eliminar(id)`, que no envíe dos veces si ya hay una eliminación en curso. En `PantallaDetalle` agrega un `IconButton` con papelera en la `TopAppBar` y un `AlertDialog` de confirmación; si el servidor confirma, vuelve a la lista. Usa los colores del tema (`primary` azul petróleo, `secondary` naranja).» | `eliminar()` en la API con `Response<Unit>`; en el Repository, si `!isSuccessful`, lanza `HttpException(respuesta)` dentro de `llamar { }`; el estado `eliminacion` en el ViewModel; la papelera y el diálogo en el detalle | Comprobé que `HttpException` quedara solo en el Repository, nunca en el ViewModel. Agregué que, al tener éxito, el ViewModel quite la publicación de la lista ya descargada. El diálogo salía lila (color por defecto de Material 3) y lo cambié a blanco con `containerColor = surface` | Con `Response<Unit>` Retrofit no lanza la excepción solo: si no se revisa `isSuccessful`, un 404 parecería un éxito. JSONPlaceholder responde 200 pero no borra nada, así que sin el cambio local la publicación seguiría en la lista. El lila no es de la paleta del diseño |
| «Implementa la mejora Editar título con PATCH. Crea `@Serializable data class CambioTitulo(val title: String)` para enviar solo el campo que cambia. En `PostApi` agrega `@PATCH("posts/{id}")` con `@Path` y `@Body` que devuelva `Post`. En el Repository, `editarTitulo(id, titulo): Result<Post>`. En el ViewModel, un `StateFlow<UiState<Post>?>` llamado `edicion`. Crea `PantallaEditar` con un `OutlinedTextField` precargado con el título actual, un botón Guardar naranja con spinner mientras envía y una tarjeta que muestre la respuesta del servidor (id, userId, title). Ábrela desde un icono de lápiz en el detalle y maneja Atrás con `BackHandler`.» | `CambioTitulo`, `editarTitulo()` en API y Repository, el estado `edicion` en el ViewModel, `PantallaEditar` con el campo, el botón y la tarjeta de respuesta, y la navegación en `MainActivity` | Hice que Guardar solo se habilite si el título cambió y no está vacío. El ViewModel actualiza el detalle y la lista con **el `Post` que devuelve el servidor**, no con el texto escrito, y sin volver a pedirlos. El campo se recuerda con `rememberSaveable(post.id)`. Al guardar se cierra el teclado con `LocalFocusManager` | Enviar un PATCH sin cambios es una petición inútil. La app debe mostrar lo que el servidor confirmó. Recordar por id evita que, al abrir otra publicación, aparezca el texto de la anterior. En el emulador, el teclado tapaba la tarjeta de respuesta |
| «Implementa la mejora Búsqueda por título sobre las publicaciones ya descargadas, sin hacer peticiones nuevas. En `PostsViewModel` agrega un `MutableStateFlow<String>` con el texto y un `StateFlow` derivado `listaVisible` que combine `lista` y `busqueda` con `combine` + `stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ...)`, filtrando solo cuando el estado es `Exito` (`contains` sin distinguir mayúsculas). Debe funcionar junto con el filtro por usuario. En `PantallaLista` agrega un `OutlinedTextField` blanco con icono de lupa debajo de los chips y dibuja `listaVisible` en lugar de `lista`.» | El estado `busqueda`, la función `buscar()`, `listaVisible` con `combine` + `stateIn` y el campo con la lupa en la lista | Agregué el botón X para borrar lo escrito, el mensaje "Sin resultados para …" (distinto de "Sin publicaciones"), `trim()` al texto y la acción Buscar del teclado para ocultarlo. Comprobé en Logcat que buscar no hace peticiones: solo aparece el `GET posts` inicial | El usuario tiene que distinguir "no hay datos" de "tu búsqueda no coincide". Un espacio al final no debe vaciar la lista. Pedir a la API en cada letra sería lento y gastaría datos |
| «Revisa el código que generaste para estas tres mejoras como lo haría el docente en la sustentación: busca imports de Retrofit, `PostApi` o `HttpException` dentro de `viewmodel/`, `catch` vacíos que traguen excepciones, llamadas a la API desde un Composable, uso de `GlobalScope` y URLs con `http://`. Indica archivo y línea de cada problema.» | No encontró incumplimientos: `HttpException` solo está en `data/PostRepository.kt` y `data/ErrorApp.kt`; las pantallas solo llaman funciones del ViewModel; todo usa `viewModelScope` | No me quedé con la respuesta de la IA: lo comprobé yo con `grep -ri "retrofit\|HttpException\|PostApi" viewmodel/` después de cada mejora y buscando `GlobalScope` en todo el proyecto | La IA también se equivoca al revisar su propio código. La regla se demuestra con el resultado del comando, no con lo que diga la IA |

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
