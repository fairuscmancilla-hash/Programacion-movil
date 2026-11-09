package com.tecsup.catalogo.data.auth
import com.google.firebase.auth.*
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CancellationException
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.catalogo.domain.model.*
import com.tecsup.catalogo.data.repository.seguro
import kotlinx.coroutines.tasks.await
class FirebaseAuthRepository : AuthRepository {
 private val auth get() = FirebaseAuth.getInstance()
 private val db get() = FirebaseFirestore.getInstance()
 override suspend fun iniciarSesion(correo: String, clave: String) = seguro {
  try {
   val usuario = requireNotNull(auth.signInWithEmailAndPassword(correo, clave).await().user)
   leerSesion(usuario.uid, usuario.email ?: correo)
  } catch (e: CancellationException) { throw e } catch (e: Exception) {
   auth.signOut()
   throw ErrorAcceso(when(e) {
    is FirebaseAuthInvalidCredentialsException, is FirebaseAuthInvalidUserException -> "Correo o contraseña incorrectos."
    is FirebaseNetworkException -> "No hay conexión a internet. Vuelve a intentarlo."
    is FirebaseTooManyRequestsException -> "Demasiados intentos. Espera unos minutos y vuelve a ingresar."
    is FirebaseFirestoreException -> "No se pudo leer tu rol. Revisa la conexión y las reglas de Firestore."
    is ErrorAcceso -> e.message ?: "La cuenta no tiene un rol válido."
    else -> "No se pudo ingresar. Revisa la configuración de Firebase."
   })
  }
 }
 override suspend fun sesionActual(): Sesion? {
  val u = auth.currentUser ?: return null
  return leerSesion(u.uid, u.email.orEmpty())
 }
 override fun cerrarSesion() = auth.signOut()
 private suspend fun leerSesion(uid: String, correo: String): Sesion {
  val doc = db.collection("usuarios").document(uid).get().await()
  val rol = when(doc.getString("rol")) { "admin" -> Rol.ADMIN; "usuario" -> Rol.USUARIO; else -> throw ErrorAcceso("Tu cuenta no tiene un rol válido en Firestore.") }
  return Sesion(uid, correo, rol)
 }
}
