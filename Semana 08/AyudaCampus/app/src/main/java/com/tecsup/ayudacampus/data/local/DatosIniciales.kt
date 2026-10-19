package com.tecsup.ayudacampus.data.local

import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.model.Rol

object DatosIniciales {

    private const val HORA = 3600 * 1000L

    suspend fun sembrar(db: AyudaDatabase) {
        if (db.usuarioDao().contar() != 0) return

        // Ids fijos para que los tickets puedan referenciarlos
        db.categoriaDao().insertarTodas(
            listOf(
                Categoria(id = 1, nombre = "Hardware", icono = "pc"),
                Categoria(id = 2, nombre = "Software", icono = "code"),
                Categoria(id = 3, nombre = "Red / Wi-Fi", icono = "wifi"),
                Categoria(id = 4, nombre = "Cuenta", icono = "key"),
                Categoria(id = 5, nombre = "Impresión", icono = "printer")
            )
        )
        db.usuarioDao().insertarTodos(
            listOf(
                Usuario(id = 1, nombre = "Juan Pérez", correo = "juan@campus.test", clave = "1234", rol = Rol.CLIENTE.name),
                Usuario(id = 2, nombre = "Soporte TI", correo = "soporte@campus.test", clave = "admin", rol = Rol.ADMIN.name)
            )
        )

        val ahora = System.currentTimeMillis()
        val dao = db.ticketDao()

        val impresion = dao.insertarTicket(
            Ticket(
                clienteId = 1, categoriaId = 5,
                titulo = "No puedo imprimir en biblioteca",
                descripcion = "La impresora de la biblioteca no recibe mis documentos desde la laptop.",
                ubicacion = "Biblioteca",
                prioridad = Prioridad.BAJA.name, estado = EstadoTicket.RESUELTO.name,
                fecha = ahora - 72 * HORA
            )
        ).toInt()
        val clave = dao.insertarTicket(
            Ticket(
                clienteId = 1, categoriaId = 4,
                titulo = "Olvidé mi contraseña del aula virtual",
                descripcion = "No recuerdo mi contraseña y la recuperación por correo no llega.",
                ubicacion = "Aula virtual",
                prioridad = Prioridad.BAJA.name, estado = EstadoTicket.CERRADO.name,
                fecha = ahora - 96 * HORA, fechaCierre = ahora - 90 * HORA, calificacion = 5
            )
        ).toInt()
        dao.insertarTicket(
            Ticket(
                clienteId = 1, categoriaId = 1,
                titulo = "Teclado no responde",
                descripcion = "El teclado de la PC 12 no escribe algunas letras.",
                ubicacion = "Aula 204",
                prioridad = Prioridad.MEDIA.name, estado = EstadoTicket.ABIERTO.name,
                fecha = ahora - 26 * HORA
            )
        )
        // ALTA + ABIERTO con más de 1 hora (1 de 2 para la alerta del panel)
        dao.insertarTicket(
            Ticket(
                clienteId = 1, categoriaId = 2,
                titulo = "Instalar Android Studio",
                descripcion = "Necesitamos Android Studio en las PCs del laboratorio 5 para el examen de mañana.",
                ubicacion = "Lab 5",
                prioridad = Prioridad.ALTA.name, estado = EstadoTicket.ABIERTO.name,
                fecha = ahora - 4 * HORA
            )
        )
        val wifi = dao.insertarTicket(
            Ticket(
                clienteId = 1, categoriaId = 3,
                titulo = "Sin Wi-Fi en el laboratorio 3",
                descripcion = "Desde ayer no hay señal de Wi-Fi en todo el laboratorio. Afecta a 30 alumnos durante la clase.",
                ubicacion = "Lab 3 · Pabellón B",
                prioridad = Prioridad.ALTA.name, estado = EstadoTicket.EN_PROCESO.name,
                fecha = ahora - 2 * HORA
            )
        ).toInt()
        // ALTA + ABIERTO con más de 1 hora (2 de 2 para la alerta del panel)
        dao.insertarTicket(
            Ticket(
                clienteId = 1, categoriaId = 1,
                titulo = "Servidor del aula 305 caído",
                descripcion = "El servidor de prácticas del aula 305 no enciende y hay examen en la tarde.",
                ubicacion = "Aula 305",
                prioridad = Prioridad.ALTA.name, estado = EstadoTicket.ABIERTO.name,
                fecha = ahora - 90 * 60 * 1000L
            )
        )

        val soporte = "Soporte TI" to Rol.ADMIN.name
        val juan = "Juan Pérez" to Rol.CLIENTE.name
        dao.insertarComentario(Comentario(ticketId = impresion, autorId = 2, autorNombre = soporte.first, autorRol = soporte.second, texto = "Reinstalamos el driver de la impresora. Ya debería imprimir.", fecha = ahora - 24 * HORA))
        dao.insertarComentario(Comentario(ticketId = clave, autorId = 2, autorNombre = soporte.first, autorRol = soporte.second, texto = "Restablecimos tu contraseña. Revisa tu correo institucional.", fecha = ahora - 92 * HORA))
        dao.insertarComentario(Comentario(ticketId = wifi, autorId = 2, autorNombre = soporte.first, autorRol = soporte.second, texto = "Revisaremos el access point del pabellón B hoy.", fecha = ahora - 100 * 60 * 1000L))
        dao.insertarComentario(Comentario(ticketId = wifi, autorId = 1, autorNombre = juan.first, autorRol = juan.second, texto = "Gracias, quedo atento.", fecha = ahora - 90 * 60 * 1000L))
    }
}
