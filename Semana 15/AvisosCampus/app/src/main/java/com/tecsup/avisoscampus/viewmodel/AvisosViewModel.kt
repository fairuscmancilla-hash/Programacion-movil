package com.tecsup.avisoscampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.avisoscampus.data.AvisosRepository
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.EventoAviso
import com.tecsup.avisoscampus.model.Origen
import com.tecsup.avisoscampus.model.PermisoUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AvisosViewModel(private val repo: AvisosRepository) : ViewModel() {

    // Mejora 3: el Repository entrega un Flow (lee de disco); el ViewModel lo convierte
    // en StateFlow para que la pantalla tenga siempre un valor y sobreviva a la rotación.
    val avisos: StateFlow<List<Aviso>> = repo.avisos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // Evento de una sola vez: "muestra esta notificación".
    // El ViewModel NO tiene Context: lo recibe la pantalla.
    // Mejora 1: el evento dice si el aviso es urgente; el canal lo elige el Notificador.
    private val _eventos = MutableSharedFlow<EventoAviso>()
    val eventos: SharedFlow<EventoAviso> = _eventos.asSharedFlow()

    private val _permiso = MutableStateFlow<PermisoUi>(PermisoUi.SinPedir)
    val permiso: StateFlow<PermisoUi> = _permiso.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    // ---------- Paso 2: estado del permiso ----------
    fun permisoRespondido(concedido: Boolean) {
        _permiso.value = if (concedido) PermisoUi.Concedido else PermisoUi.Denegado
        // Paso 8: cuando el permiso pasa a Concedido, el mensaje de error se quita solo
        if (concedido) _mensaje.value = null
    }

    // ---------- Paso 3: aviso inmediato ----------
    fun avisarAhora() {
        viewModelScope.launch {
            val aviso = repo.registrar("Tarea lista", "Tu entrega fue registrada", Origen.BOTON)
            // Mejora 2: texto largo que se lee al expandir la notificación
            _eventos.emit(EventoAviso(aviso, detalle = DETALLE_TAREA))
        }
    }

    // ---------- Paso 5: recordatorio con cuenta regresiva ----------
    private var trabajo: Job? = null
    private val _faltan = MutableStateFlow<Int?>(null)
    val faltan: StateFlow<Int?> = _faltan.asStateFlow()

    fun recordarEn(segundos: Int = 10) {
        if (trabajo?.isActive == true) return
        trabajo = viewModelScope.launch {
            try {
                for (s in segundos downTo 1) {
                    _faltan.value = s
                    delay(1000)
                }
                val aviso = repo.registrar(
                    "Recordatorio",
                    "Revisa tu laboratorio pendiente",
                    Origen.RECORDATORIO
                )
                // Mejora 1: el recordatorio es urgente y sale por el canal Urgentes
                _eventos.emit(EventoAviso(aviso, urgente = true, detalle = DETALLE_RECORDATORIO))
            } finally {
                // Termine bien o cancelado, ya no hay recordatorio activo
                _faltan.value = null
            }
        }
    }

    fun cancelarRecordatorio() {
        trabajo?.cancel()
    }

    // ---------- Paso 6: escuchar el modo avión ----------
    private val _escuchandoAvion = MutableStateFlow(false)
    val escuchandoAvion: StateFlow<Boolean> = _escuchandoAvion.asStateFlow()

    fun cambiarEscucha(valor: Boolean) {
        _escuchandoAvion.value = valor
    }

    fun alCambiarModoAvion(activo: Boolean) {
        viewModelScope.launch {
            val aviso = repo.registrar(
                "Modo avión",
                if (activo) "Activado" else "Desactivado",
                Origen.MODO_AVION
            )
            _eventos.emit(EventoAviso(aviso))
        }
    }

    // ---------- Paso 8: permiso denegado ----------
    fun noSePudoMostrar() {
        _mensaje.value = "Sin permiso: el aviso se guardó en el historial, pero no se mostró"
    }

    // ---------- Mejora 2: textos largos (datos ficticios) ----------
    private companion object {
        const val DETALLE_TAREA =
            "Tu entrega fue registrada. El docente revisará tu laboratorio y la nota " +
                "aparecerá en el aula virtual durante la semana. Si necesitas corregir " +
                "algo, vuelve a subir tu repositorio antes de la fecha límite."
        const val DETALLE_RECORDATORIO =
            "Revisa tu laboratorio pendiente. Antes de entregar, prueba la app en el " +
                "emulador, completa el README con las capturas y sube tus commits a GitHub."
    }
}
