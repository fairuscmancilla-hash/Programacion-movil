package com.tecsup.catalogo
import android.content.Context
import androidx.room.Room
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.catalogo.data.local.AppDatabase
import com.tecsup.catalogo.data.local.PreferenciasInicio
import com.tecsup.catalogo.data.remote.RetrofitClient
import com.tecsup.catalogo.data.repository.*
import com.tecsup.catalogo.data.auth.*
import com.tecsup.catalogo.notification.*
import com.tecsup.catalogo.ui.login.LoginViewModel
import com.tecsup.catalogo.ui.lista.ListaViewModel
import com.tecsup.catalogo.ui.detalle.DetalleViewModel
import com.tecsup.catalogo.ui.favoritos.FavoritosViewModel
import com.tecsup.catalogo.ui.admin.AdminViewModel
class AppContainer(context: Context) {
 private val db = Room.databaseBuilder(context, AppDatabase::class.java, "nexio.db").build()
 val repo: ItemRepository = ItemRepositoryImpl(RetrofitClient.crear(), db)
 private val inicio=PreferenciasInicio(context)
 val auth: AuthRepository = FirebaseAuthRepository()
 val notificadorAndroid = NotificadorAndroid(context)
 val notificador: Notificador = notificadorAndroid
 fun factory(id: Int = 0) = object : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST") override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass) {
   LoginViewModel::class.java -> LoginViewModel(auth,inicio)
   ListaViewModel::class.java -> ListaViewModel(repo, notificador)
   DetalleViewModel::class.java -> DetalleViewModel(repo, id)
   FavoritosViewModel::class.java -> FavoritosViewModel(repo)
   AdminViewModel::class.java -> AdminViewModel(repo, notificador)
   else -> error("ViewModel desconocido")
  } as T
 }
}
