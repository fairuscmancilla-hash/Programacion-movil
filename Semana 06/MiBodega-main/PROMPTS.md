# PROMPTS UTILIZADOS - MIBODEGA

## Fase 2 - Mejora con Inteligencia Artificial

Durante la Fase 2, se utilizó Inteligencia Artificial como asistente de desarrollo únicamente para realizar dos mejoras específicas de experiencia de usuario (UX) sobre la funcionalidad del buscador existente en la pantalla de Inicio.

---

### Mejora 1: Botón para limpiar el texto de búsqueda

#### Prompt utilizado:
> "Hola, en la pantalla de Inicio de la app MiBodega ya tengo un buscador de productos funcionando. Me gustaría agregarle un botón con el ícono de una 'X' dentro del campo de texto (`OutlinedTextField`), de modo que solo sea visible cuando haya texto escrito. Al presionar esta 'X', debe limpiar inmediatamente el estado `textoBusqueda` dejando la casilla vacía, para que el usuario pueda volver a ver la lista de productos sin necesidad de borrar caracter por caracter. ¿Cómo puedo agregar esta funcionalidad usando Jetpack Compose?"

#### Explicación breve del resultado:
La IA sugirió utilizar la propiedad `trailingIcon` del `OutlinedTextField`, envolviendo un `IconButton` con el ícono `Icons.Default.Close` dentro de una condición `if (textoBusqueda.isNotEmpty())`. Al hacer clic en el botón, el estado `textoBusqueda` se reinicia a `""`, permitiendo restablecer la búsqueda de forma rápida e intuitiva.

---

### Mejora 2: Estado visual cuando no existen resultados de búsqueda

#### Prompt utilizado:
> "Cuando realizo una búsqueda por nombre o selecciono una categoría en MiBodega y no hay ningún producto que coincida, el área de productos se queda completamente en blanco y no le da ninguna respuesta al usuario. Quisiera agregar una pantalla o mensaje de estado vacío (empty state) centrado que muestre un ícono de búsqueda, el título 'No se encontraron productos' y el mensaje secundario 'Prueba con otro nombre o cambia la categoría'. ¿Cómo puedo implementar esta validación condicional antes de renderizar la grilla de productos en Jetpack Compose?"

#### Explicación breve del resultado:
La IA propuso evaluar si la lista resultante `productosOrdenados.isEmpty()` es verdadera. Si está vacía, se renderiza un componente `Box` centrado con un `Column` que contiene el ícono `Icons.Default.Search`, el título principal y el texto de ayuda. En caso contrario (`else`), se renderiza el `LazyVerticalGrid` habitual con las tarjetas de productos.

---

## Resultado Final

Las mejoras realizadas con Inteligencia Artificial no reemplazaron ni modificaron la estructura lógica original del buscador. Se conservó intacto el funcionamiento conjunto y en tiempo real entre la búsqueda por nombre de producto y el filtro dinámico por categorías. La intervención de la IA se enfocó exclusivamente en enriquecer la interfaz de usuario y la retroalimentación visual (UX).
