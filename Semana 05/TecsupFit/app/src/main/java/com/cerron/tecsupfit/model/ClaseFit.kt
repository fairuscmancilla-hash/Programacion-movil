package com.cerron.tecsupfit.model

data class ClaseFit(
    val id: Int,
    val nombre: String,
    val horario: String,
    val sala: String,
    val duracion: String,
    val cuposDisponibles: Int,
    val cuposTotales: Int,
    val descripcion: String,
    val categoria: String
)

val clasesMock = listOf(
    ClaseFit(
        id = 1,
        nombre = "Yoga funcional",
        horario = "7:00 am",
        sala = "Sala 2",
        duracion = "45 min",
        cuposDisponibles = 10,
        cuposTotales = 12,
        descripcion = "Clase de yoga funcional para mejorar movilidad, equilibrio y flexibilidad.",
        categoria = "Hoy"
    ),
    ClaseFit(
        id = 2,
        nombre = "Cross Training",
        horario = "6:00 pm",
        sala = "Sala 1",
        duracion = "45 min",
        cuposDisponibles = 8,
        cuposTotales = 12,
        descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
        categoria = "Hoy"
    ),
    ClaseFit(
        id = 3,
        nombre = "Spinning",
        horario = "7:30 pm",
        sala = "Sala 3",
        duracion = "50 min",
        cuposDisponibles = 6,
        cuposTotales = 12,
        descripcion = "Entrenamiento cardiovascular en bicicleta estacionaria.",
        categoria = "Hoy"
    ),
    ClaseFit(
        id = 4,
        nombre = "Pilates",
        horario = "5:00 pm",
        sala = "Sala 2",
        duracion = "50 min",
        cuposDisponibles = 9,
        cuposTotales = 12,
        descripcion = "Sesión enfocada en fuerza, postura y control corporal.",
        categoria = "Esta semana"
    ),
    ClaseFit(
        id = 5,
        nombre = "Full Body",
        horario = "6:30 pm",
        sala = "Sala 1",
        duracion = "45 min",
        cuposDisponibles = 7,
        cuposTotales = 12,
        descripcion = "Rutina de cuerpo completo con ejercicios funcionales.",
        categoria = "Esta semana"
    ),
    ClaseFit(
        id = 6,
        nombre = "Baile Fit",
        horario = "7:00 pm",
        sala = "Sala 3",
        duracion = "50 min",
        cuposDisponibles = 10,
        cuposTotales = 15,
        descripcion = "Clase cardiovascular con música y movimientos de baile.",
        categoria = "Esta semana"
    )
)

val filtrosClases = listOf(
    "Hoy",
    "Esta semana"
)
