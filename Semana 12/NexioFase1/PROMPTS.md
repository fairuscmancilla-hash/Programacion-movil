Actúa como desarrollador Android senior. Tengo una app en Kotlin con Jetpack Compose + Material 3,
arquitectura MVVM, Retrofit (DummyJSON, categoría smartphones), Room y Firebase Auth + Firestore.
Paquete: com.tecsup.catalogo. Es la Fase 2 (con IA) del Laboratorio 11-12 y debo cumplir la rúbrica:
requisito R12, mejoras de errores e interfaz, mínimo 3 commits y PROMPTS.md.

Contexto del código actual:
- ui/UiState.kt: sealed interface UiState<out T> { Cargando; Exito(datos); Error(mensaje) }
- data/repository/ItemRepository.kt (interface): items: Flow<List<Item>>, favoritos: Flow<List<Item>>,
  refrescar(): Result<Int>, obtenerDetalle(id): Result<Item>, alternarFavorito(id),
  cantidadGuardada(), cantidadFavoritos(), limpiarCache()
- notification/Notificador.kt (interface): mostrar(titulo, texto)
- ui/lista/ListaViewModel.kt: recibe ItemRepository y Notificador; expone estado: StateFlow<UiState<List<Item>>>
  y aviso: StateFlow<String?>. Al iniciar observa repo.items y llama refrescar(). Si el refresco sale bien
  muestra la lista y notifica "Se guardaron N elementos"; si falla sin datos guardados muestra Error;
  si falla con datos guardados muestra la lista y el aviso "Sin conexión: mostrando datos guardados".
- ui/detalle/DetalleViewModel.kt: ante cualquier error muestra "No se pudo cargar el detalle. Revisa tu conexión."
- ui/lista/ListaScreen.kt: el composable Catalogo(...) se reutiliza en Favoritos y, si la lista está vacía,
  siempre muestra "Aún no hay productos aquí."

Haz estos cambios, respetando MVVM (el ViewModel no recibe Context y depende solo de interfaces):

1. Mensajes de error:
   - Crea ui/MensajesError.kt con una función mensajeDeError(e: Throwable): String que devuelva:
     IOException -> "No hay conexión a internet"; HttpException 404 -> "No se encontró el elemento solicitado";
     otro HttpException -> "El servidor respondió con error {código}"; cualquier otro -> "Ocurrió un error inesperado".
   - Úsala en ListaViewModel (estado Error sin datos guardados) y en DetalleViewModel.

2. Pruebas unitarias del ViewModel de la lista (R12), en app/src/test/java/com/tecsup/catalogo/ui/lista/:
   - Agrega a app/build.gradle.kts: testImplementation("junit:junit:4.13.2") y
     testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0").
   - FakeItemRepository: implementa ItemRepository con un MutableStateFlow en memoria (como si fuera Room)
     y una propiedad respuestaApi: Result<List<Item>> para simular la API. Cuenta cuántas veces se refrescó
     y guarda los ids de favoritos alternados.
   - FakeNotificador: implementa Notificador y guarda los mensajes en una lista en vez de mostrarlos.
   - ListaViewModelTest con StandardTestDispatcher y Dispatchers.setMain/resetMain, con estas pruebas:
     a) al abrir muestra Cargando hasta que termina el primer refresco;
     b) con internet muestra la lista y notifica "Se guardaron 3 elementos";
     c) sin internet y sin datos guardados muestra Error "No hay conexión a internet" y no notifica;
     d) sin internet con datos guardados muestra esos datos y el aviso de sin conexión;
     e) error HTTP 500 sin datos muestra "El servidor respondió con error 500";
     f) Reintentar después de un error carga la lista;
     g) alternarFavorito actualiza la lista desde el repositorio.

3. Mejora de interfaz:
   - Agrega a Catalogo un parámetro mensajeVacio con valor por defecto "Aún no hay productos aquí.",
     centrado y con el color del tema.
   - En FavoritosScreen pásale: "Aún no tienes favoritos.\nToca el corazón ♡ de un producto para guardarlo aquí."

4. Documentación:
   - Crea PROMPTS.md con la plantilla de la guía (Fecha, Herramienta, Prompt, Qué respondió,
     Qué usamos / qué cambiamos y por qué, Qué aprendimos).
   - Agrega al README una sección "Fase 2" con una tabla de las mejoras, cómo correr las pruebas
     (gradlew testDebugUnitTest) y una captura de Favoritos vacío.

5. Comprueba que el proyecto compile, que pasen las pruebas unitarias y las instrumentadas,
   y que lint no tenga errores.

6. Organiza los cambios en commits pequeños con formato "tipo: qué se hizo" en la rama mejora-ia-semana12:
   fix (errores), test (pruebas), feat (interfaz) y docs (PROMPTS.md y README).
