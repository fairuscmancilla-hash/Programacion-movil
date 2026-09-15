# Laboratorio 04 - Manejo de Estados

## Descripción

Aplicación desarrollada en Kotlin con Jetpack Compose para practicar el manejo de estados.

La aplicación permite controlar una temperatura utilizando botones para aumentar, disminuir y resetear el valor.

## Funcionalidades

- Temperatura inicial de 20 °C.
- Botón para aumentar la temperatura.
- Botón para disminuir la temperatura.
- Botón para resetear el valor a 20 °C.
- El texto cambia a rojo cuando la temperatura es mayor a 30 °C.
- El texto cambia a azul cuando la temperatura es menor a 10 °C.

## Conceptos utilizados

- `remember`
- `mutableStateOf`
- Recomposición en Jetpack Compose
- Eventos mediante `onClick`
- Composables
- Material Design 3

## Evidencias

### Temperatura normal

Temperatura inicial de 20 °C.

### Temperatura baja

Cuando la temperatura es menor a 10 °C, el texto se muestra de color azul.

### Temperatura alta

Cuando la temperatura supera los 30 °C, el texto se muestra de color rojo.

## Autor

Yajaira Cerron