# PROMPTS · Fase 2

**Herramienta usada:** Claude Code. La guía propone Gemini en Android Studio, pero las mejoras de esta fase se hicieron con Claude Code, por eso la segunda columna indica lo que respondió esta herramienta.

**Rama:** `mejora-ia-semana15`

| Prompt que escribí | Qué respondió Claude Code | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| «listo ahora la fase 2 con ia me ayudas hacerlo pero tienes que hacerlo paso a paso» | Preguntó qué mejoras hacer, quién haría los commits y el nombre de la rama. Elegí **Dos canales**, **Texto largo** e **Historial persistente**, hacer yo los commits y la rama `mejora-ia-semana15` | Se descartaron Botón en la notificación, Receptor del cargador y Push de prueba. La rama no se llamó `mejora-ia` | La guía pide mínimo 3 mejoras y que al menos una cambie el Repository o el ViewModel: el historial persistente cambia los dos. La rama `mejora-ia` ya existe en el repositorio con otro laboratorio y en las semanas anteriores usé `mejora-ia-semanaNN` |
| Misma conversación (mejora 1: dos canales) | Creó el canal «Urgentes» con `IMPORTANCE_HIGH` junto al canal «Avisos» y agregó `EventoAviso(aviso, urgente)`: el ViewModel marca el recordatorio como urgente y el `Notificador` elige el canal | El ViewModel no recibe el id del canal ni ninguna constante del `Notificador`, solo un `Boolean` | El ViewModel decide **qué** avisar y no debe saber nada del sistema de notificaciones. Así sigue sin `Context` ni `NotificationManager` |
| Pegué el error de `git add "Semana 15/AvisosCampus"` | Explicó que la terminal estaba dentro de `AvisosCampus` y que había que usar `git add .` | Desde ahí todos los comandos se corrieron dentro de la carpeta del laboratorio | La ruta era relativa a `Programacion-movil`. `git add .` solo agrega lo que está en `AvisosCampus` |
| «sigue» (mejora 2: texto largo) | Avisó que el commit de la mejora 1 se había llevado el borrado de un README de Semana 13 que ya estaba preparado (*staged*), y dio los comandos para rehacerlo. Luego agregó `BigTextStyle` con un texto largo y el origen como resumen | Rehice el commit con `git reset --soft HEAD~1` y `git restore --staged` para que solo tuviera archivos de la Semana 15. El texto largo lo decide el ViewModel (`detalle` en `EventoAviso`) y el historial guarda el texto corto | Un commit debe tener solo los cambios de su mejora. El `Notificador` solo construye la notificación; qué se dice es decisión del ViewModel |
| Pegué el `git log` de los dos commits (mejora 3: historial persistente) | Usó DataStore Preferences: `avisos` pasó a `Flow`, `registrar` a `suspend` y el ViewModel usa `stateIn`. Creó `AvisosRepositoryDataStore` | El DataStore lo crea `AvisosApp` y se le entrega al Repository, que no recibe `Context`. Se guarda también el siguiente id, se limita a 50 avisos y un error de lectura muestra el historial vacío | El Repository no debe usar `Context` (tabla de capas de la guía). Si el id volviera a 1 al reabrir la app, una notificación nueva reemplazaría a una anterior con el mismo id. Se usó DataStore y no Room porque solo es una lista pequeña |
| «Listo, sigue» | Creó este archivo con los prompts reales de la fase y agregó la sección de la Fase 2 al README | No se registraron respuestas de Gemini | Gemini no se usó en estas mejoras |

## Comprobación

Cada mejora se compiló y se probó en el emulador Pixel_5 (Android 15) antes de su commit:

- **Dos canales:** el canal `urgentes` tiene importancia 4 y `avisos` importancia 3. El recordatorio aparece emergente sobre la pantalla de inicio; «Tarea lista» sigue en el canal normal.
- **Texto largo:** contraída se ve «Tu entrega fue registrada»; al expandirla, el párrafo completo.
- **Historial persistente:** se generaron 4 avisos, se cerró la app con `force-stop` y al reabrirla seguían los 4. El siguiente aviso tomó el id 5.

Revisión de las reglas de la guía: no hay `Context`, `NotificationManager` ni `BroadcastReceiver` en `viewmodel/` ni en `data/`. El receptor del modo avión se sigue quitando en `onDispose` y el `PendingIntent` sigue siendo `FLAG_IMMUTABLE`.
