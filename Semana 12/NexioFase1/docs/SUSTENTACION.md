# Guía para la sustentación individual

## Recorrido de cinco minutos
1. Ingresar como usuario: mostrar catálogo y detalle; explicar los dos endpoints.
2. Marcar un favorito y actualizar: explicar por qué la marca se conserva.
3. Mostrar la consulta sin conexión y el aviso de caché.
4. Ingresar como administrador: mostrar totales, sincronizar y la notificación. Limpiar caché solo durante una prueba deliberada, porque borra también favoritos.
5. Explicar las capas y señalar el código de las interfaces.

## Preguntas y respuestas

**¿Por qué el repositorio es una interface?**
El ViewModel conoce operaciones, no la implementación de Retrofit o Room. Se puede reemplazar la fuente de datos para probarla sin cambiar la pantalla.

**¿Por qué la lista sale de Room?**
Para tener una sola fuente de verdad: lo que se sincroniza y lo que se usa sin conexión se observa desde la misma base local. Retrofit actualiza esa base.

**¿Cómo se conservan los favoritos al sincronizar?**
ItemRepositoryImpl descarga primero. Después, dentro de db.withTransaction, lee los IDs favoritos y los reaplica a las entidades antes del Upsert. Las actualizaciones de favorito no pueden intercalarse dentro de esa transacción.

**¿Dónde se decide el rol?**
FirebaseAuthRepository lee el campo rol de usuarios/{uid} en Firestore. La UI usa Rol para mostrar destinos. Las reglas de Firestore permiten leer solo el documento del propio UID y prohíben cambiar roles desde clientes.

**¿Qué pasa si se rechaza el permiso de notificaciones?**
La sincronización y el catálogo siguen funcionando. NotificadorAndroid descarta el aviso pendiente. Si se acepta el permiso después de terminar la primera descarga, entrega una sola vez el aviso que quedó esperando.

**¿Cuál es la diferencia entre sincronizar y limpiar caché?**
Sincronizar actualiza Room conservando favoritos. Limpiar elimina productos y favoritos de este dispositivo; no borra usuarios ni documentos de Firebase.

**¿Qué ocurre sin conexión?**
Si existe caché, se mantiene visible con aviso. Si está vacía, aparece Error con Reintentar. Un fallo HTTP se distingue de la falta de conexión.

**¿Qué son DTO, Entity e Item?**
ItemDto describe el JSON. ItemEntity describe la tabla local. Item es el modelo que consume la UI. Mappers convierte entre representaciones y aplica las traducciones locales.

**¿Los precios son comerciales reales en Perú?**
No. Son datos de una API de prueba, presentados en soles con una tasa fija de demostración de S/ 3.45 por dólar. El detalle lo aclara. Room conserva el precio original en dólares para no convertir dos veces.

**¿Qué conserva el estado al girar?**
ViewModelProvider mantiene los ViewModels a través del cambio de configuración. AppContainer vive en NexioApplication para no recrear la base de datos en cada giro. Las pantallas recogen StateFlow con collectAsStateWithLifecycle.

**¿Qué pruebas se hicieron?**
Consultar la sección Validación del README y docs/PRUEBAS_ACEPTACION.md. Las pruebas de integración incluidas usan Room en memoria y una API controlada; no sustituyen la comprobación real de Firebase y de la interfaz.

**¿Dónde están los commits?**
En esta prueba la estudiante pidió expresamente no crearlos. No se debe inventar un historial ni atribuir aportes a otra persona. La parte de Git se realizará cuando sea autorizada.
