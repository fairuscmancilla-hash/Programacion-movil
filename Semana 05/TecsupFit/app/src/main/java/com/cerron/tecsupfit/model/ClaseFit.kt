package com.cerron.tecsupfit.model

data class ClaseFit(
    val id: Int,
    val nombre: String,
    val entrenador: String,
    val horario: String,
    val duracion: String,
    val cupos: Int,
    val categoria: String
)

val clasesMock = listOf(
    ClaseFit(
        id = 1,
        nombre = "Funcional",
        entrenador = "Carlos Mendoza",
        horario = "08:00 AM",
        duracion = "45 min",
        cupos = 8,
        categoria = "Hoy"
    ),
    ClaseFit(
        id = 2,
        nombre = "Spinning",
        entrenador = "Andrea Torres",
        horario = "10:30 AM",
        duracion = "50 min",
        cupos = 5,
        categoria = "Hoy"
    ),
    ClaseFit(
        id = 3,
        nombre = "Yoga",
        entrenador = "Lucía Ramos",
        horario = "06:00 PM",
        duracion = "60 min",
        cupos = 12,
        categoria = "Esta semana"
    ),
    ClaseFit(
        id = 4,
        nombre = "Cross Training",
        entrenador = "Diego Flores",
        horario = "07:30 PM",
        duracion = "45 min",
        cupos = 6,
        categoria = "Esta semana"
    )
)

val filtrosClases = listOf(
    "Hoy",
    "Esta semana"
)
