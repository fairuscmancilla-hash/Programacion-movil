# Ejecutar en el emulador

Usar un emulador con Google Play, como requiere la guía. Si Android Studio muestra "device offline" o "Can't find service: package", comprobar el arranque de Android antes de modificar el proyecto: en esta sesión falló el sistema del emulador y se recuperó mediante arranque en frío y renderizado por software.

Pasos de recuperación:
1. Device Manager: detener el dispositivo y seleccionar Cold Boot.
2. Si vuelve a fallar gráficamente, editar el dispositivo y seleccionar Software en la configuración gráfica.
3. Volver a ejecutar la app cuando Android termine de arrancar.

No usar Wipe Data para un problema de conexión sin antes considerar que elimina las cuentas y los datos locales del dispositivo virtual. No hace falta cambiar a un plan Firebase de pago para probar NEXIO.

El proyecto usa compileSdk 36 y targetSdk 35. El emulador usado en la validación tiene API 37.2. El aviso de targetSdk y los avisos de dependencias disponibles de Lint no impiden compilar el APK; no se ocultaron ni se aplicaron actualizaciones de bibliotecas ajenas a esta fase.
