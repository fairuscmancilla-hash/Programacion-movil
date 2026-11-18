package com.cerron.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cerron.centrotareas.data.TareasRepository
import com.cerron.centrotareas.model.CargaUi
import com.cerron.centrotareas.model.PrimosUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {

    // ----- Tarea con progreso (pasos 1 a 4)
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    // Paso 3: avance de la tarea, de 0f a 1f
    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    // Paso 4: referencia a la tarea en curso y estado de ejecución
    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    // Paso 2: la espera corre en segundo plano y el hilo principal queda libre
    fun tareaLarga() {
        if (_corriendo.value) return
        trabajo = viewModelScope.launch {
            _corriendo.value = true
            try {
                _progreso.value = 0f
                _mensaje.value = "Trabajando..."
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                repo.tareaConProgreso().collect { avance ->
                    _progreso.value = avance
                }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    // ----- Contar primos (paso 5)
    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()
    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (_primos.value is PrimosUi.Calculando) return
        trabajoPrimos = viewModelScope.launch {
            _primos.value = PrimosUi.Calculando()
            val inicio = System.currentTimeMillis()
            try {
                var cantidad = 0
                repo.contarPrimosConAvance(hasta).collect { avance ->
                    cantidad = avance.cantidad
                    _primos.value = PrimosUi.Calculando(avance.porcentaje)
                }
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    // ----- Dos cargas (pasos 6 y 7)
    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    fun cargarUnaPorUna() {
        ejecutarCarga("Una por una") {
            val perfil = repo.cargarPerfil()
            val pedidos = repo.cargarPedidos(_simularError.value)
            Pair(perfil, pedidos)
        }
    }

    fun cargarALaVez() {
        ejecutarCarga("A la vez") {
            coroutineScope {
                val perfil = async { repo.cargarPerfil() }
                val pedidos = async { repo.cargarPedidos(_simularError.value) }
                Pair(perfil.await(), pedidos.await())
            }
        }
    }

    private fun ejecutarCarga(
        modo: String,
        cargar: suspend () -> Pair<String, List<String>>
    ) {
        if (_carga.value is CargaUi.Cargando) return
        viewModelScope.launch {
            _carga.value = CargaUi.Cargando
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = cargar()
                _carga.value = CargaUi.Exito(
                    modo = modo,
                    perfil = perfil,
                    pedidos = pedidos.size,
                    milisegundos = System.currentTimeMillis() - inicio
                )
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _carga.value = CargaUi.Error("No se pudo cargar: ${e.message}")
            }
        }
    }

    // Paso 7: interruptor que hace fallar la carga de pedidos
    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }
}
