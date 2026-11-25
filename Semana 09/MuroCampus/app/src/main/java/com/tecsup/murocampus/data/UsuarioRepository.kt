package com.tecsup.murocampus.data

import com.tecsup.murocampus.data.remote.UsuarioApi
import com.tecsup.murocampus.model.Usuario
import kotlinx.coroutines.CancellationException

// [E] Ejemplo resuelto: Repository de usuarios que devuelve Result.
interface UsuarioRepository {
    suspend fun listar(): Result<List<Usuario>>
}

class UsuarioRepositoryRemote(private val api: UsuarioApi) : UsuarioRepository {
    override suspend fun listar(): Result<List<Usuario>> =
        try {
            Result.success(api.listar())
        } catch (e: CancellationException) {
            throw e                       // nunca se traga la cancelacion
        } catch (e: Exception) {
            Result.failure(e.aErrorApp())   // se traduce a ErrorApp
        }
}
