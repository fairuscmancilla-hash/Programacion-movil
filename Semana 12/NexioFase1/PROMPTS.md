# PROMPTS.md · Uso de IA

Registro de cada consulta a la IA durante el laboratorio 11-12 (NEXIO).
Herramienta usada en todas las consultas: **Claude Code (Claude Opus 5.5)**, en la app de escritorio de Claude.

> Completa la parte **"Qué aprendimos"** de cada consulta con tus propias palabras antes de entregar.

## Consulta 1
**Fecha:** 08/11/2026
**Herramienta:** Claude Code (Claude Opus 5.5)
**Prompt:** «Puedes revisar mi proyecto, solo la fase sin IA. La integrante soy sola yo, pero quiero que sigas tal cual indica [la guía GLAB-S11].»
**Qué respondió (resumen):** Comparó el proyecto con la guía requisito por requisito. R01 a R11 estaban cumplidos. Faltaban los commits por hito y había diferencias con la guía: Retrofit usaba Gson en vez de kotlinx-serialization, Room estaba en 2.7.0 en vez de 2.8.5, la interface `Notificador` tenía un método extra y había documentos sueltos.
**Qué usamos / qué cambiamos y por qué:** Usé la revisión como lista de pendientes. No cambié nada en esta consulta; decidí qué corregir en la siguiente.
**Qué aprendimos:** _(escribe aquí con tus palabras: por ejemplo, qué requisitos de la rúbrica revisa el docente y por qué el Repository debe ser una interface)_

## Consulta 2
**Fecha:** 08/11/2026
**Herramienta:** Claude Code (Claude Opus 5.5)
**Prompt:** «Actualiza el README. Las capturas no se visualizan, puedes revisar eso. Haz el punto 2 y 3 [dependencias y Notificador], y el punto 4 júntalo en el README. El rediseño lo puedes eliminar.»
**Qué respondió (resumen):** Cambió Retrofit a `converter-kotlinx-serialization` con OkHttp y `logging-interceptor`, y subió Room a 2.8.5. Dejó `Notificador` solo con `mostrar(titulo, texto)`, como en el contrato, y movió la respuesta al permiso a `MainActivity`. Reescribió el README con todo lo que pide la guía y una sección de extras, y quitó los documentos sueltos. Arregló una prueba instrumentada que fallaba a veces por tiempos de Android.
**Qué usamos / qué cambiamos y por qué:** Usé todos los cambios porque ponen el proyecto igual a la guía. Comprobé que compila, que las 15 pruebas instrumentadas pasan y que la lista y el detalle cargan en el emulador. Las capturas no se veían porque el visor que usaba no carga imágenes locales; en GitHub sí se ven.
**Qué aprendimos:** _(escribe aquí: por ejemplo, para qué sirve `ignoreUnknownKeys` en kotlinx-serialization y por qué el ViewModel solo debe conocer la interface Notificador)_

## Consulta 3
**Fecha:** 08/11/2026
**Herramienta:** Claude Code (Claude Opus 5.5)
**Prompt:** «Me puedes dar los comandos para hacer los commits.» / «¿Los commits que me das son de acuerdo a la rúbrica del PDF?»
**Qué respondió (resumen):** Preparó 17 commits que siguen la tabla de hitos H1–H16 de la guía, más uno de pruebas, con el formato `tipo: qué se hizo`. Explicó qué parte del criterio "Git y GitHub" cumplen y en qué se diferencia del repositorio que pide la guía.
**Qué usamos / qué cambiamos y por qué:** Ejecuté yo misma los comandos para que los commits salieran desde mi cuenta.
**Qué aprendimos:** _(escribe aquí: por ejemplo, qué significa "un commit = una idea" y cómo revisar los commits con `git log` o `git shortlog -sn`)_

## Consulta 4
**Fecha:** 08/11/2026
**Herramienta:** Claude Code (Claude Opus 5.5)
**Prompt:** «Esta es mi dirección [repositorio Programacion-movil], ahí tengo que subir mi proyecto, en la semana 12. Primero quiero crear una nueva rama semana12. El proyecto debe quedar en una sola carpeta, como TecsupStore.»
**Qué respondió (resumen):** Dio los comandos para clonar, crear la rama `semana12`, copiar el proyecto sin `google-services.json` ni `local.properties`, y moverlo a `Semana 12/NexioFase1` con `git mv` para conservar el historial. También explicó por qué Android Studio mostraba otra rama y que hay que llevar `google-services.json` aparte para iniciar sesión en otra computadora.
**Qué usamos / qué cambiamos y por qué:** Ejecuté los comandos y subí la rama `semana12`.
**Qué aprendimos:** _(escribe aquí: por ejemplo, por qué `google-services.json` no se sube a GitHub y la diferencia entre `git mv` y copiar archivos)_

## Consulta 5
**Fecha:** 08/11/2026
**Herramienta:** Claude Code (Claude Opus 5.5)
**Prompt:** «Ahora puedes ejecutar la fase con IA, pero hazlo de acuerdo a la rúbrica del laboratorio.»
**Qué respondió (resumen):** Hizo la Fase 2 según la guía:
1. **R12:** 7 pruebas unitarias de `ListaViewModel` con un `FakeItemRepository` y un `FakeNotificador`. Usan `kotlinx-coroutines-test` (`StandardTestDispatcher` y `Dispatchers.setMain`).
2. **Errores:** creó la función `mensajeDeError` para la lista y el detalle. El detalle ya no dice "Revisa tu conexión" cuando el error es del servidor.
3. **Interfaz:** Favoritos vacío ahora explica cómo agregar un favorito.

Comprobó que compila, que las 7 pruebas unitarias y las 15 instrumentadas pasan, y revisó la pantalla de Favoritos en el emulador.
**Qué usamos / qué cambiamos y por qué:** Usé las tres mejoras y las subí en la rama `mejora-ia-semana12`. Elegí ese nombre porque mi repositorio ya tenía una rama `mejora-ia` de otra semana.
**Qué aprendimos:** _(escribe aquí: por ejemplo, para qué sirve un repositorio falso en una prueba, por qué hay que cambiar `Dispatchers.Main` y qué comprueba cada prueba)_

## Reflexión general
_(escribe aquí en 4 o 5 líneas: en qué te ayudó la IA, qué tuviste que revisar o corregir tú, qué riesgo tiene usar código de la IA sin entenderlo y qué harías distinto la próxima vez)_
