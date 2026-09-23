# Prompt usado en Gemini (Android Studio → Gemini)

Pega este prompt en Gemini junto con el código de la rama `sin-ia`
(LoginScreen, HomeScreen, ListScreen, DetailScreen, ProfileScreen, Alumno.kt y Theme.kt).
Toma captura del prompt y de la respuesta para tu informe.

```
Actúa como diseñador UI/UX experto en Jetpack Compose y Material 3.
Tengo una app Android en Kotlin (paquete com.example.semana05_navegacion) con
Navigation Compose 2.7.7 (NavHost + sealed class Screen con las rutas login, home,
list, profile y detail/{itemId} de tipo Int). Las pantallas son LoginScreen,
HomeScreen, ListScreen, DetailScreen(itemId: Int) y ProfileScreen.

Mejora SOLO la presentación visual. NO cambies la navegación, las rutas, el login
(correo @tecsup.edu.pe y contraseña de 6+ caracteres), la búsqueda ni el cierre de sesión.

PALETA (crea un object PortalColors en screens/Componentes.kt):
- Primario #5B4A9E, PrimarioOscuro #3F3175, Lavanda #E9E3FF, Rosa #8E5A7A, Peligro #C0392B
- Degradados: fondoLogin (Lavanda→Blanco), fondoHome (PrimarioOscuro→Primario→Lavanda→Blanco),
  cabecera (PrimarioOscuro→Primario), cabeceraPerfil horizontal (Primario→Rosa)
- En Theme.kt pon dynamicColor = false para que siempre se vea morado.

COMPONENTES REUTILIZABLES (Componentes.kt):
- AvatarIniciales(iniciales, size, conBorde): círculo con degradado Primario→Rosa y las iniciales en blanco.
- FotoAlumno(nombreFoto, iniciales, size, conBorde): foto circular cargada desde res/drawable
  buscando el recurso por nombre (alumno_1 … alumno_8). La imagen se reduce con
  BitmapFactory (inSampleSize, inScaled=false) para no gastar memoria.
  Si la foto no existe, muestra AvatarIniciales (la app nunca se cae).
- FilaInfo(icono, etiqueta, valor): ícono en caja lavanda redondeada + etiqueta gris pequeña + valor en negrita.

MODELO: agrega a data class Alumno los campos foto: String ("alumno_1"…"alumno_8")
y bio: String, y una propiedad de extensión iniciales ("Juan León" -> "JL").

PANTALLAS:
1. LoginScreen → "Portal Académico" / "Accede a tu cuenta": tarjeta blanca centrada
   (esquinas 24dp, elevación 8dp) sobre fondoLogin; campos con íconos Email y Lock,
   botón mostrar/ocultar contraseña (Visibility/VisibilityOff), error en rojo,
   botón "INICIAR SESIÓN" morado de 52dp y el texto "Usa tu correo @tecsup.edu.pe".
2. HomeScreen → fondo fondoHome, "Bienvenido,\n{nombre}" blanco en negrita,
   "¿Qué deseas gestionar hoy?", dos tarjetas blancas con ícono (Groups y Person),
   título, subtítulo y flecha: "Directorio de Alumnos" y "Mi Perfil Académico";
   abajo un TextButton rojo con ícono Logout: "Cerrar Sesión Segura".
3. ListScreen → TopAppBar lavanda "Directorio de Alumnos", buscador redondeado con
   lupa y botón limpiar, contador "N alumno(s) encontrado(s)" y tarjetas con
   FotoAlumno de 52dp, nombre en negrita, carrera en morado y flecha.
4. DetailScreen → "Expediente Académico": cabecera con degradado y esquinas inferiores
   redondeadas, FotoAlumno de 110dp con borde blanco superpuesto, nombre y carrera,
   tarjeta lavanda clara con FilaInfo (ID Estudiante "2026-000X", correo, carrera, ciclo)
   y sección "Biografía".
5. ProfileScreen → "Configuración de Perfil": cabecera cabeceraPerfil con FotoAlumno
   de 90dp (del alumno cuyo correo coincide con la sesión) y el nombre;
   secciones "INFORMACIÓN PERSONAL" y "ACADÉMICO" con FilaInfo;
   botón con borde "Ir al inicio" y botón rojo suave "Cerrar Sesión".

Usa solo Material 3 y material-icons-extended (sin librerías externas de imágenes).
Devuelve el código completo de cada archivo con todos sus imports.
```

## Prompt de las fotos (generadas con IA en Higgsfield · modelo Soul 2, 1:1)

Las 8 fotos son de personas ficticias. Plantilla usada:

```
Headshot portrait photo of a young Latino man/woman, [edad] years old, [cabello],
[expresión], wearing [ropa], plain light grey background, soft studio lighting,
photorealistic, centered face
```
