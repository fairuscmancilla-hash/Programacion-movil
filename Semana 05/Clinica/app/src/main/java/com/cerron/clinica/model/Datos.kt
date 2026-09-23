package com.cerron.clinica.model

// Modelo para almacenar los datos de cada médico
data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val descripcion: String
)

// Lista de médicos de prueba
val doctoresMock = listOf(

    Medico(
        id = 1,
        nombre = "Dra. Ana Torres",
        especialidad = "Cardiología",
        calificacion = 4.9,
        descripcion = "Especialista en prevención, diagnóstico y tratamiento de enfermedades cardiovasculares."
    ),

    Medico(
        id = 2,
        nombre = "Dr. Luis Vega",
        especialidad = "Pediatría",
        calificacion = 4.7,
        descripcion = "Especialista en atención médica y cuidado integral de niños y adolescentes."
    ),

    Medico(
        id = 3,
        nombre = "Dra. Rosa Díaz",
        especialidad = "Dermatología",
        calificacion = 4.8,
        descripcion = "Especialista en diagnóstico y tratamiento de enfermedades de la piel."
    )
)

// Especialidades utilizadas en el filtro de la pantalla principal
val especialidades = listOf(
    "Todos",
    "Cardiología",
    "Pediatría",
    "Dermatología"
)

// Fechas disponibles para agendar una cita
val fechasDisponibles = listOf(
    "Jue 26",
    "Vie 27",
    "Sáb 28"
)

// Horarios disponibles
val horasDisponibles = listOf(
    "9:00",
    "10:30",
    "3:00"
)