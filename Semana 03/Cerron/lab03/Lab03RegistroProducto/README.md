## Mejora con IA

| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |
|---|---|---|
| Agrega validación de campos vacíos en `PantallaRegistro`. Si falta un dato al presionar AGREGAR PRODUCTO, muestra un mensaje de error en rojo en lugar de la Card. También agrega un botón LIMPIAR que vacíe nombre, precio y cantidad. No cambies la estructura principal de la pantalla. | Se agregó una variable de estado para mostrar errores, una validación de campos vacíos en el botón AGREGAR PRODUCTO y un botón LIMPIAR que reinicia los campos y oculta el resumen. | Acepté la validación de campos vacíos y el botón LIMPIAR. Después corregí la lógica para validar también que precio sea un número válido y cantidad sea un número entero, evitando registrar valores como letras. |