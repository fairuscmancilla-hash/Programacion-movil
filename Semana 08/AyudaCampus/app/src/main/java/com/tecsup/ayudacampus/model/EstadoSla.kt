package com.tecsup.ayudacampus.model

/** Tiempo que le queda a un ticket según su prioridad (Alta 4 h, Media 24 h, Baja 72 h). */
data class EstadoSla(
    val restanteMs: Long,
    val limiteHoras: Int
) {
    val vencido: Boolean get() = restanteMs <= 0
    /** Le queda menos del 25 % del tiempo */
    val porVencer: Boolean get() = !vencido && restanteMs < limiteHoras * 3_600_000L / 4
}
