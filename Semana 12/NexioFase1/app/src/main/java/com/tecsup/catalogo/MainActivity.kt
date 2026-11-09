package com.tecsup.catalogo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.tecsup.catalogo.ui.navigation.AppNav
import com.tecsup.catalogo.ui.theme.NexioTheme
class MainActivity : ComponentActivity() {
 private val container get() = (application as NexioApplication).container
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  FirebaseApp.initializeApp(this)
  // La respuesta al permiso va al notificador concreto; los ViewModel solo ven la interface Notificador.
  setContent { NexioTheme { AppNav(container) { concedido -> container.notificadorAndroid.actualizarPermiso(concedido) } } }
 }
}
