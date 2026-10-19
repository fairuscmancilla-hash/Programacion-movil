package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.ayudacampus.data.repository.AyudaRepository

/** Crea los ViewModels de la app. Se agrega un caso por cada ViewModel nuevo. */
class AyudaViewModelFactory(
    private val repo: AyudaRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        CategoriasViewModel::class.java -> CategoriasViewModel(repo)
        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    } as T
}
