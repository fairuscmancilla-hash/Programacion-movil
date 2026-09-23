package com.example.semana05_navegacion.data


// Modelo de datos de un alumno
data class Alumno(
    val id: Int,
    val nombre: String,
    val carrera: String,
    val correo: String,
    val ciclo: String,
    val foto: String, // nombre del archivo en res/drawable (foto generada con IA)
    val bio: String = "Estudiante de Tecsup con interés en el desarrollo de soluciones tecnológicas."
)

// Iniciales para el avatar: "Juan León" -> "JL"
val Alumno.iniciales: String
    get() = nombre.split(" ").take(2).joinToString("") { it.take(1).uppercase() }

// Fuente de datos en memoria (reemplaza a la lista (1..8) del laboratorio)
object AlumnoRepository {

    val alumnos = listOf(
        Alumno(1, "Juan León", "Diseño y Desarrollo de Software", "juan.leon@tecsup.edu.pe", "VI Ciclo", "alumno_1", "Estudiante destacado con interés en el desarrollo Android."),
        Alumno(2, "María García", "Big Data y Ciencia de Datos", "maria.garcia@tecsup.edu.pe", "IV Ciclo", "alumno_2"),
        Alumno(3, "Carlos Pérez", "Redes y Comunicaciones", "carlos.perez@tecsup.edu.pe", "III Ciclo", "alumno_3"),
        Alumno(4, "Ana López", "Diseño y Desarrollo de Software", "ana.lopez@tecsup.edu.pe", "II Ciclo", "alumno_4"),
        Alumno(5, "Luis Ramírez", "Ciberseguridad", "luis.ramirez@tecsup.edu.pe", "V Ciclo", "alumno_5"),
        Alumno(6, "Sofía Torres", "Big Data y Ciencia de Datos", "sofia.torres@tecsup.edu.pe", "VI Ciclo", "alumno_6"),
        Alumno(7, "Diego Castillo", "Redes y Comunicaciones", "diego.castillo@tecsup.edu.pe", "I Ciclo", "alumno_7"),
        Alumno(8, "Valeria Rojas", "Ciberseguridad", "valeria.rojas@tecsup.edu.pe", "IV Ciclo", "alumno_8")
    )

    fun buscarPorId(id: Int): Alumno? = alumnos.find { it.id == id }

    // RF03: filtra por nombre o carrera sin importar mayúsculas
    fun filtrar(texto: String): List<Alumno> =
        if (texto.isBlank()) alumnos
        else alumnos.filter {
            it.nombre.contains(texto, ignoreCase = true) ||
                it.carrera.contains(texto, ignoreCase = true)
        }
}

// Sesión simple en memoria: guarda el correo del usuario que inició sesión
object Sesion {
    var correo: String = ""

    // "juan.leon@tecsup.edu.pe" -> "Juan Leon"
    val nombre: String
        get() = correo.substringBefore("@")
            .split(".", "_")
            .filter { it.isNotBlank() }
            .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

    fun cerrar() {
        correo = ""
    }
}

// RF01: reglas de validación del login
object ValidadorLogin {
    const val DOMINIO = "@tecsup.edu.pe"

    fun validar(correo: String, password: String): String? = when {
        correo.isBlank() || password.isBlank() -> "Completa todos los campos"
        !correo.trim().endsWith(DOMINIO) -> "Usa tu correo institucional ($DOMINIO)"
        correo.trim().substringBefore("@").isBlank() -> "Correo no válido"
        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
        else -> null // null = sin errores
    }
}
