package com.cerron.clinica.model

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val descripcion: String
)

val doctoresMock = listOf(
    Medico(
        id = 1,
        nombre = "Dra. Ana Torres",
        especialidad = "Cardiología",
        calificacion = 4.9,
        descripcion = "Especialista en cardiología y prevención de enfermedades cardiovasculares."
    ),

    Medico(
        id = 2,
        nombre = "Dr. Luis Vega",
        especialidad = "Pediatría",
        calificacion = 4.7,
        descripcion = "Especialista en atención integral y cuidado de niños y adolescentes."
    ),

    Medico(
        id = 3,
        nombre = "Dra. Rosa Díaz",
        especialidad = "Dermatología",
        calificacion = 4.8,
        descripcion = "Especialista en diagnóstico y tratamiento de enfermedades de la piel."
    )
)

val especialidades = listOf(
    "Cardiología",
    "Pediatría",
    "Dermatología"
)

val fechasDisponibles = listOf(
    "Jue 26",
    "Vie 27",
    "Sáb 28"
)

val horasDisponibles = listOf(
    "9:00",
    "10:30",
    "3:00"
)
