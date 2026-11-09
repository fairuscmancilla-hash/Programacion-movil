# Pruebas de aceptación de fase 1

Fecha: 8 de noviembre de 2026, contexto America/Lima. Dispositivo: Medium Phone con imagen Google Play, API 37.2. Cuentas creadas en Firebase por la estudiante; las contraseñas fueron ingresadas por ella, sin guardarlas en este proyecto.

| Caso de la guía | Resultado | Comprobación realizada | Evidencia |
|---|---|---|---|
| PA-01 | Aprobado | Ingreso real de usuario@nexio.com realizado por la estudiante: catálogo visible y sin opción Admin en la barra inferior. | [Captura](capturas/02-lista-usuario.png) |
| PA-02 | Aprobado | Ingreso real de admin@nexio.com realizado por la estudiante; catálogo y acceso Admin visibles. | [Captura](capturas/02-lista-admin.png) |
| PA-03 | Aprobado | Una clave ficticia incorrecta fue rechazada por Firebase; mensaje Correo o contraseña incorrectos y sin acceso al catálogo. | [Captura](capturas/08-clave-incorrecta.png) |
| PA-04 | Aprobado | Campos vacíos muestran Completa correo y contraseña; el retorno temprano del ViewModel evita llamar al repositorio. | [Captura](capturas/07-campos-vacios.png) |
| PA-05 | Aprobado | Carga y catálogo reales; sincronización de 16 elementos y notificación con canal/permiso. La cola inicial se prueba adicionalmente en instrumentación. | [Captura](capturas/06-notificacion.png) |
| PA-06 | Aprobado | Detalle real del iPhone 13 Pro: segundo endpoint en el repositorio, precio en soles y descripción en español visibles. | [Captura](capturas/03-detalle.png) |
| PA-07 | Aprobado | Oppo A57 marcado, proceso cerrado y reabierto: continúa en Favoritos. | [Captura](capturas/12-favorito-reabrir.png) |
| PA-08 | Aprobado | Modo avión con caché: catálogo visible y aviso Sin conexión; no se borran productos. | [Captura](capturas/08-offline-con-cache.png) |
| PA-09 | Aprobado | Modo avión sin caché, con sesión vigente: Error y Reintentar. Reintentar vuelve a manejar el fallo sin caída. | [Captura](capturas/09-offline-sin-cache.png) |
| PA-10 | Aprobado | Sincronización real completada; Oppo A57 continúa en Favoritos y el total sigue en 1. | [Captura](capturas/05-admin.png) |
| PA-11 | Aprobado | Limpiar deja lista vacía y totales 0/0; después de recuperar internet, sincronizar recupera 16 productos y 0 favoritos. | [Captura](capturas/10-cache-vacia.png) |
| PA-12 | Aprobado | Cerrar sesión y cerrar/reabrir el proceso muestra directamente el formulario de login; no se restaura el catálogo sin credenciales. | [Captura](capturas/15-sesion-cerrada.png) |
| PA-13 | Aprobado | Giro real a horizontal: filtro Oppo y sesión Admin conservados, sin nuevo estado Cargando; ViewModel retenido por Activity y AppContainer por Application. | [Captura](capturas/11-rotacion.png) |
| PA-14 | Aprobado | La estudiante denegó el permiso: POST_NOTIFICATIONS granted=false y el catálogo de 16 productos siguió funcionando. Posteriormente se habilitó para probar la notificación. | [Captura](capturas/02-lista-admin.png) |

## Alcance exacto de las pruebas
- PA-09 se ejecutó con una sesión ya autenticada y la caché del catálogo vacía después de Limpiar caché. Se comprobó la misma condición de primera descarga sin datos locales; no se pretende que Firebase pueda autenticar una cuenta nueva sin internet.
- PA-13 comprobó giro real a 2400 x 1080, persistencia de filtro y sesión. La conservación del ViewModel evita repetir su inicializador; no se instaló un interceptor para contar paquetes de red.
- PA-14: el permiso estuvo denegado (POST_NOTIFICATIONS granted=false) mientras el catálogo funcionaba. Después se concedió para comprobar el aviso. Las pruebas instrumentadas adicionales verifican aceptar un permiso con un aviso pendiente y descartar el aviso al denegarlo.
- La notificación real mostró «Se guardaron 16 elementos». Se probó tocarla desde el inicio de Android: abrió NEXIO.
- Las imágenes se tomaron en diferentes estados de prueba. Por eso el panel de una captura puede mostrar un favorito y el de limpieza muestra cero.
- Durante las pruebas se marcó Oppo A57 como favorito. Se comprobó su persistencia y luego se ejecutó la limpieza deliberada. Al terminar se restauró la copia local de prueba con 16 productos y 0 favoritos; las cuentas de Firebase no se eliminaron.

## Repetir los casos
1. Probar primero usuario y administrador con sus cuentas correctas y verificar la barra inferior.
2. Cerrar sesión, intentar una clave incorrecta y probar campos vacíos.
3. Sincronizar con permiso concedido; revisar la notificación y tocarla.
4. Marcar un favorito, cerrar/reabrir, sincronizar y comprobar que persiste.
5. Con datos guardados, activar modo avión y actualizar: debe aparecer el aviso con catálogo visible.
6. Con una sesión vigente, limpiar caché, activar modo avión y actualizar: deben verse Error y Reintentar. Restaurar la red y reintentar.
7. En Admin, comprobar totales cero después de limpiar y recuperación al sincronizar. Esta operación elimina favoritos; usar datos de prueba.
8. Girar la pantalla con un filtro escrito y comprobar que se conserva.
9. Cerrar sesión y reabrir: debe aparecer el formulario de ingreso, sin acceso al catálogo.
10. Probar la denegación del permiso en un dispositivo de prueba; el catálogo debe continuar funcionando.
