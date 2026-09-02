Estoy desarrollando el Laboratorio 02 del curso de Programación Móvil utilizando Kotlin en Android Studio.

Ya tengo desarrollada una primera versión de un programa de consola llamado "Carrito de Compras - Tienda TECSUP". Ahora quiero realizar una segunda versión del mismo ejercicio utilizando Inteligencia Artificial como apoyo y aplicando Programación Orientada a Objetos (POO).

El programa original trabaja con los siguientes productos:

1. Laptop HP - S/ 2500.00 - cantidad 1
2. Mouse Logitech - S/ 45.50 - cantidad 2
3. Teclado - S/ 120.00 - cantidad 1
4. Audífonos Gamer - S/ 85.50 - cantidad 2

Quiero mantener las funcionalidades principales del carrito:

- Agregar productos.
- Mostrar el detalle del carrito.
- Calcular el subtotal.
- Calcular el IGV del 18%.
- Calcular el total.
- Mostrar la cantidad de productos.
- Identificar el producto más caro.
- Aplicar descuentos.
- Buscar un producto.
- Eliminar un producto.
- Mostrar nuevamente el carrito y sus totales después de eliminar un producto.

Quiero mejorar la estructura del programa aplicando Programación Orientada a Objetos.

Crea una clase Producto que represente los productos mediante los atributos nombre, precio y cantidad.

Crea una clase CarritoCompras que encapsule una lista privada de productos y que contenga métodos para:

- agregarProducto()
- buscarProducto()
- eliminarProducto()
- obtenerProductos()
- calcularSubtotal()
- calcularIGV()
- calcularTotal()
- mostrarDetalle()
- aplicarDescuento()

Además, quiero implementar POLIMORFISMO para manejar los descuentos.

Crea una interfaz llamada PoliticaDescuento que tenga los métodos:

calcular(total: Double): Double
descripcion(): String

Implementa diferentes clases que utilicen esta interfaz:

- SinDescuento
- Descuento5Porciento
- Descuento10Porciento

Cada clase debe implementar el mismo método calcular(), pero debe realizar un cálculo diferente dependiendo de la política de descuento.

La clase CarritoCompras debe poder recibir cualquier objeto de tipo PoliticaDescuento y utilizarlo para calcular el descuento, demostrando así el uso de polimorfismo.

También quiero que el programa seleccione una política de descuento de acuerdo con el total de la compra.

Finalmente, modifica el main() para utilizar los objetos creados y mostrar por consola:

- Nombre del cliente.
- Productos agregados.
- Detalle del carrito.
- Cantidad de productos.
- Subtotal.
- IGV del 18%.
- Total a pagar.
- Producto más caro.
- Política de descuento aplicada.
- Monto del descuento.
- Total con descuento.
- Búsqueda de un producto.
- Eliminación de un producto.
- Carrito actualizado.
- Nuevo total después de eliminar el producto.

El programa debe continuar siendo ejecutado por consola dentro del proyecto de Android Studio.

Mantén el código sencillo, ordenado y fácil de entender para un estudiante que está aprendiendo Kotlin.

Explícame también dónde se están aplicando los conceptos de Programación Orientada a Objetos, especialmente encapsulamiento, interfaces y polimorfismo.