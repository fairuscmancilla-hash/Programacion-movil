package com.tecsup.catalogo.ui.login
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.tecsup.catalogo.data.auth.AuthRepository
import com.tecsup.catalogo.data.local.InicioRepository
import com.tecsup.catalogo.domain.model.Sesion
import com.tecsup.catalogo.ui.UiState
class LoginViewModel(private val auth: AuthRepository,private val inicio:InicioRepository) : ViewModel() {
 private val _estado = MutableStateFlow<UiState<Sesion?>>(UiState.Cargando)
 val estado = _estado.asStateFlow()
 private val _mostrarIngreso=MutableStateFlow(inicio.ingresoHabilitado())
 val mostrarIngreso=_mostrarIngreso.asStateFlow()
 fun comenzar(){inicio.habilitarIngreso();_mostrarIngreso.value=true}
 fun verBienvenida(){_mostrarIngreso.value=false}
 init { viewModelScope.launch { try { _estado.value = UiState.Exito(auth.sesionActual()) } catch (e: CancellationException) { throw e } catch (_: Exception) { _estado.value = UiState.Exito(null) } } }
 fun ingresar(correo: String, clave: String) {
  if(correo.isBlank() || clave.isBlank()) { _estado.value = UiState.Error("Completa correo y contraseña."); return }
  if(!correo.contains("@") || !correo.substringAfter("@").contains('.')) { _estado.value = UiState.Error("Ingresa un correo válido."); return }
  viewModelScope.launch {
   _estado.value = UiState.Cargando
   _estado.value = auth.iniciarSesion(correo.trim(), clave).fold({ UiState.Exito(it) }, { UiState.Error(it.message ?: "No se pudo ingresar. Vuelve a intentarlo.") })
  }
 }
 fun cerrarSesion() { inicio.habilitarIngreso();_mostrarIngreso.value=true;try { auth.cerrarSesion() } finally { _estado.value = UiState.Exito(null) } }
}
