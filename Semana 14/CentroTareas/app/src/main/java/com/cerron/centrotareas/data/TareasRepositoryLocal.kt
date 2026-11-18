package com.cerron.centrotareas.data

import android.util.Log
import com.cerron.centrotareas.model.AvancePrimos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class TareasRepositoryLocal : TareasRepository {

    override fun tareaConProgreso(): Flow<Float> = flow {
        Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
        for (i in 1..100) {
            delay(40)
            emit(i / 100f)
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun cargarPerfil(): String {
        delay(2000)
        return "Ana Torres (ficticia)"
    }

    override suspend fun cargarPedidos(simularError: Boolean): List<String> {
        delay(3000)
        if (simularError) throw IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    override fun contarPrimosConAvance(hasta: Int): Flow<AvancePrimos> = flow {
        var cantidad = 0
        var ultimoPorcentaje = -1
        for (n in 2..hasta) {
            currentCoroutineContext().ensureActive()
            if (esPrimo(n)) cantidad++
            val porcentaje = (n.toLong() * 100 / hasta).toInt()
            if (porcentaje != ultimoPorcentaje) {
                ultimoPorcentaje = porcentaje
                emit(AvancePrimos(porcentaje, cantidad))
            }
        }
    }.flowOn(Dispatchers.Default)

    private fun esPrimo(n: Int): Boolean {
        if (n < 2) return false
        var d = 2
        while (d.toLong() * d <= n) {
            if (n % d == 0) return false
            d++
        }
        return true
    }
}
