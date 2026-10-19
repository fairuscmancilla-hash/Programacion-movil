package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.entity.rolEnum
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ErroresRegistro(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val confirmar: String? = null
) {
    val hayErrores: Boolean get() = listOf(nombre, correo, clave, confirmar).any { it != null }
}

class SesionViewModel(
    private val repo: AyudaRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /** true cuando ya se revisó si había un perfil guardado (lo usa el Splash). */
    private val _inicioListo = MutableStateFlow(false)
    val inicioListo: StateFlow<Boolean> = _inicioListo.asStateFlow()

    val modoOscuro: StateFlow<Boolean> =
        prefs.modoOscuro.stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val notificaciones: StateFlow<Boolean> =
        prefs.notificaciones.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    val recibirTickets: StateFlow<Boolean> =
        prefs.recibirTickets.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    init {
        viewModelScope.launch {
            val perfil = prefs.perfilActivo.first()
            if (perfil.isNotEmpty()) {
                val u = repo.obtenerUsuario(prefs.usuarioId.first())
                if (u != null && u.rol == perfil) _usuarioActual.value = u else prefs.cerrarSesion()
            }
            _inicioListo.value = true
        }
    }

    fun login(correo: String, clave: String, rolSeleccionado: Rol, onExito: (Rol) -> Unit) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Ingresa tu correo y tu contraseña"
            return
        }
        viewModelScope.launch {
            val u = repo.login(correo.trim(), clave)
            when {
                u == null -> _error.value = "Correo o contraseña incorrectos"
                u.rol != rolSeleccionado.name ->
                    _error.value = "Esta cuenta no es de ${rolSeleccionado.etiqueta}. Elige la otra pestaña."
                else -> {
                    _error.value = null
                    prefs.guardarSesion(u.rol, u.id)
                    _usuarioActual.value = u
                    onExito(u.rolEnum())
                }
            }
        }
    }

    fun limpiarError() {
        _error.value = null
    }

    // ---------- Registro de estudiante ----------
    private val _erroresRegistro = MutableStateFlow(ErroresRegistro())
    val erroresRegistro: StateFlow<ErroresRegistro> = _erroresRegistro.asStateFlow()

    fun limpiarErroresRegistro() {
        _erroresRegistro.value = ErroresRegistro()
    }

    /** Valida, crea la cuenta (correo único) y deja la sesión iniciada como estudiante. */
    fun registrar(nombre: String, correo: String, clave: String, confirmar: String, onExito: (Rol) -> Unit) {
        val c = correo.trim()
        val errores = ErroresRegistro(
            nombre = if (nombre.isBlank()) "Ingresa tu nombre" else null,
            correo = if (!Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(c)) "Ingresa un correo válido" else null,
            clave = if (clave.length < 4) "La contraseña debe tener al menos 4 caracteres" else null,
            confirmar = if (confirmar != clave) "Las contraseñas no coinciden" else null
        )
        _erroresRegistro.value = errores
        if (errores.hayErrores) return
        viewModelScope.launch {
            val u = repo.registrarEstudiante(nombre.trim(), c, clave)
            if (u == null) {
                _erroresRegistro.value = ErroresRegistro(correo = "Este correo ya está registrado")
                return@launch
            }
            prefs.guardarSesion(u.rol, u.id)
            _usuarioActual.value = u
            onExito(u.rolEnum())
        }
    }

    /** Modo práctica: guarda el otro perfil y avisa para navegar a su grafo. */
    fun cambiarPerfil(onListo: (Rol) -> Unit) {
        val actual = _usuarioActual.value ?: return
        viewModelScope.launch {
            val otro = repo.obtenerUsuarioPorRol(actual.rolEnum().otro()) ?: return@launch
            prefs.guardarSesion(otro.rol, otro.id)
            _usuarioActual.value = otro
            onListo(otro.rolEnum())
        }
    }

    fun cerrarSesion(onListo: () -> Unit) {
        viewModelScope.launch {
            prefs.cerrarSesion()
            onListo()
            _usuarioActual.value = null
        }
    }

    fun cambiarModoOscuro(b: Boolean) {
        viewModelScope.launch { prefs.guardarModoOscuro(b) }
    }

    fun cambiarNotificaciones(b: Boolean) {
        viewModelScope.launch { prefs.guardarNotificaciones(b) }
    }

    fun cambiarRecibirTickets(b: Boolean) {
        viewModelScope.launch { prefs.guardarRecibirTickets(b) }
    }
}
