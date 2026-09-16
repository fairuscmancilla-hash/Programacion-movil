# Laboratorio 04 - Carrito de Compras con Jetpack Compose

## Descripción

En este laboratorio se desarrolló una aplicación de carrito de compras utilizando Kotlin y Jetpack Compose.

La aplicación permite:

- Registrar productos con nombre, precio y cantidad.
- Mostrar los productos en una lista dinámica.
- Calcular el importe de cada producto.
- Eliminar productos del carrito.
- Calcular el subtotal.
- Calcular el IGV del 18%.
- Calcular el total de la compra.
- Mostrar un mensaje cuando el carrito está vacío.

---

## Evidencia 1 - Carrito vacío

![Carrito vacío](capturas/carrito_vacio.png)

En este estado todavía no se han agregado productos. Se muestra el mensaje:

**"Tu carrito está vacío - Agrega tu primer producto"**

El total permanece en **S/ 0.00**.

---

## Evidencia 2 - Carrito con 3 productos

![Carrito con productos](capturas/carrito_productos.png)

Se agregaron tres productos al carrito y la aplicación actualizó automáticamente el subtotal, IGV y total.

---

# Preguntas conceptuales

## 1. ¿Por qué `productos` se declara con `val` si la lista cambia?

Se utiliza `val` porque la referencia a la lista no cambia. Lo que cambia es su contenido, ya que podemos agregar o eliminar productos.

```kotlin
val productos = remember {
    mutableStateListOf<Producto>()
}
