package com.tecsup.avisoscampus.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Mejora 3: historial que se guarda en disco con DataStore y no se pierde al cerrar la app.
// Recibe el DataStore ya creado: el Repository no usa Context.
class AvisosRepositoryDataStore(
    private val dataStore: DataStore<Preferences>
) : AvisosRepository {

    override val avisos: Flow<List<Aviso>> = dataStore.data
        .catch { error ->
            // Si el archivo no se puede leer, se muestra el historial vacío en vez de cerrar la app
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { prefs -> decodificar(prefs[CLAVE_AVISOS]) }

    override suspend fun registrar(titulo: String, texto: String, origen: Origen): Aviso {
        lateinit var nuevo: Aviso
        // edit es atómico: lee el siguiente id, agrega el aviso y guarda todo junto
        dataStore.edit { prefs ->
            val id = prefs[CLAVE_SIGUIENTE] ?: 1
            val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            nuevo = Aviso(id, titulo, texto, origen, hora)
            val lista = listOf(nuevo) + decodificar(prefs[CLAVE_AVISOS]) // lo más reciente arriba
            prefs[CLAVE_AVISOS] = codificar(lista.take(MAXIMO))
            // El id sigue creciendo entre sesiones: las notificaciones nunca repiten id
            prefs[CLAVE_SIGUIENTE] = id + 1
        }
        return nuevo
    }

    // Cada aviso se guarda como una línea de texto con sus campos separados.
    private fun codificar(avisos: List<Aviso>): String =
        avisos.joinToString(SEP_AVISO) { a ->
            listOf(a.id.toString(), a.titulo, a.texto, a.origen.name, a.hora)
                .joinToString(SEP_CAMPO)
        }

    private fun decodificar(texto: String?): List<Aviso> {
        if (texto.isNullOrEmpty()) return emptyList()
        return texto.split(SEP_AVISO).mapNotNull { linea ->
            val c = linea.split(SEP_CAMPO)
            val id = c.getOrNull(0)?.toIntOrNull()
            val origen = runCatching { Origen.valueOf(c.getOrElse(3) { "" }) }.getOrNull()
            // Un registro dañado se descarta en lugar de romper todo el historial
            if (c.size != 5 || id == null || origen == null) null
            else Aviso(id, c[1], c[2], origen, c[4])
        }
    }

    private companion object {
        val CLAVE_AVISOS = stringPreferencesKey("avisos")
        val CLAVE_SIGUIENTE = intPreferencesKey("siguiente_id")
        const val MAXIMO = 50
        const val SEP_CAMPO = "\u001F" // separador de unidad (no aparece en los textos)
        const val SEP_AVISO = "\u001E" // separador de registro
    }
}
