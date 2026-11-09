package com.tecsup.catalogo.ui.lista
import com.tecsup.catalogo.notification.Notificador

/** Notificador falso: en vez de mostrar una notificación, guarda el texto. */
class FakeNotificador : Notificador {
 val mostrados = mutableListOf<Pair<String, String>>()
 override fun mostrar(titulo: String, texto: String) { mostrados += titulo to texto }
}
