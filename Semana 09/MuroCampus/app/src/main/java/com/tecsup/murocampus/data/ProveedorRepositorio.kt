package com.tecsup.murocampus.data

import com.tecsup.murocampus.data.remote.RetrofitClient

/**
 * Arma el Repository con su fuente de datos. Vive en data/ para que la carpeta
 * viewmodel/ (incluida la Factory) no tenga que importar el cliente de red.
 */
object ProveedorRepositorio {
    val posts: PostRepository by lazy { PostRepositoryRemote(RetrofitClient.api) }
}
