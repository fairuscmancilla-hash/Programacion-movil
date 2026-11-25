package com.tecsup.avisoscampus.data

import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Mejora 3: avisos pasa a ser un Flow y registrar es suspend,
// porque guardar en disco (DataStore) no es instantáneo.
interface AvisosRepository {
    val avisos: Flow<List<Aviso>>
    suspend fun registrar(titulo: String, texto: String, origen: Origen): Aviso
}

// Versión en memoria de la Fase 1: se conserva para pruebas sin Android.
class AvisosRepositoryMemoria : AvisosRepository {
    private val _avisos = MutableStateFlow<List<Aviso>>(emptyList())
    override val avisos: StateFlow<List<Aviso>> = _avisos.asStateFlow()
    private var siguiente = 1

    override suspend fun registrar(titulo: String, texto: String, origen: Origen): Aviso {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val aviso = Aviso(siguiente++, titulo, texto, origen, hora)
        _avisos.value = listOf(aviso) + _avisos.value // lo más reciente arriba
        return aviso
    }
}
