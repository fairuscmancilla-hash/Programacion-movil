# PROMPTS.md - TECSUP Store

## Fase 2 - Mejora con Inteligencia Artificial

### Prompt utilizado

Estoy desarrollando una aplicación Android llamada TECSUP Store utilizando
Kotlin y Jetpack Compose.

Actualmente la aplicación cuenta con una pantalla principal que muestra una
lista de productos y un NavigationDrawer con las opciones Inicio, Mis pedidos,
Favoritos y Perfil.

Necesito mejorar la aplicación sin eliminar las funcionalidades que ya están
implementadas.

Realiza las siguientes mejoras:

1. Implementa la gestión de productos favoritos.

Cada producto de la pantalla principal tiene un DropdownMenu que se abre al
presionar el botón de tres puntos.

El menú debe contener como mínimo las opciones:

- Favoritos
- Compartir
- Reportar

Cuando el usuario seleccione "Favoritos", el producto debe agregarse a una
lista de productos favoritos.

El mismo producto no debe agregarse más de una vez.

Los productos agregados deben mostrarse posteriormente en la pantalla
Favoritos.

2. Implementa un contador de favoritos.

En la opción "Favoritos" del NavigationDrawer debe mostrarse un Badge con la
cantidad actual de productos que han sido agregados a favoritos.

El contador debe actualizarse automáticamente cuando se agregue un producto.

Si no existen productos favoritos, el Badge no debe mostrarse.

3. Mantén la navegación mediante NavigationDrawer.

El menú lateral debe contener las siguientes opciones:

- Inicio
- Mis pedidos
- Favoritos
- Perfil
- Cerrar sesión

Al seleccionar una opción debe mostrarse la pantalla correspondiente y
cerrarse automáticamente el NavigationDrawer.

La opción actualmente seleccionada debe resaltarse visualmente.

4. Personaliza el encabezado del NavigationDrawer.

El encabezado debe mostrar:

- Avatar con las iniciales "YC"
- Nombre: Yajaira Cerron
- Correo: yajaira@tecsup.edu.pe

5. Mejora la pantalla Perfil.

La pantalla debe mostrar:

- Avatar con las iniciales YC
- Nombre del usuario
- Correo electrónico
- Rol: Estudiante
- Institución: TECSUP
- Estado: Cuenta activa

6. Agrega la opción Cerrar sesión.

Debido a que el proyecto no cuenta con un sistema de autenticación, la opción
Cerrar sesión debe funcionar de manera simulada regresando a la pantalla
Inicio y cerrando el NavigationDrawer.

7. Mejora el diseño visual de la aplicación.

Utiliza una interfaz basada en tonos morados similar a la referencia visual
del laboratorio.

El diseño debe aplicarse principalmente a:

- TopAppBar
- NavigationDrawer
- Elemento seleccionado del Drawer
- Badge de Favoritos
- Tarjetas
- Pantalla Perfil

Mantén una apariencia limpia, moderna y consistente utilizando Material 3.

8. Mantén el proyecto organizado.

Trabaja principalmente con los siguientes archivos:

- MainActivity.kt
- AppDrawer.kt
- InicioScreen.kt
- FavoritosScreen.kt
- PerfilScreen.kt
- PedidosScreen.kt
- Producto.kt
- ProductosData.kt
- Color.kt
- Theme.kt

No elimines las funcionalidades existentes de la aplicación.

Utiliza estado observable de Jetpack Compose para que los cambios en
Favoritos y en el contador se reflejen automáticamente en la interfaz.


## Resultado obtenido

Con apoyo de IA se logró mejorar TECSUP Store incorporando:

- DropdownMenu contextual en los productos.
- Opción para agregar productos a Favoritos.
- Pantalla para visualizar los productos favoritos.
- Control para evitar productos favoritos duplicados.
- Badge dinámico con la cantidad de favoritos.
- NavigationDrawer con navegación entre pantallas.
- Encabezado personalizado con información del usuario.
- Resaltado de la opción actualmente seleccionada.
- Pantalla Perfil con información del usuario.
- Opción Cerrar sesión simulada.
- Paleta visual morada basada en el diseño del laboratorio.
- Integración de los estados de Jetpack Compose para actualizar la interfaz.

La aplicación mantiene una estructura organizada mediante componentes,
pantallas, modelos, datos y archivos de tema.
