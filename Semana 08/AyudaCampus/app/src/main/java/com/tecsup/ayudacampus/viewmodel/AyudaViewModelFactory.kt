package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository

/**
 * Crea cualquier ViewModel de la app.
 * usuario: el usuario con sesión; id: ticketId cuando la pantalla lo necesita; filtro: estado inicial de la bandeja.
 */
class AyudaViewModelFactory(
    private val repo: AyudaRepository,
    private val prefs: PreferenciasStore,
    private val usuario: Usuario? = null,
    private val id: Int = 0,
    private val filtro: String = ""
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        SesionViewModel::class.java -> SesionViewModel(repo, prefs)
        CategoriasViewModel::class.java -> CategoriasViewModel(repo)
        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    } as T
}
