package com.tecsup.catalogo.notification
import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.tecsup.catalogo.MainActivity
import com.tecsup.catalogo.R
/** Conserva el último aviso mientras se resuelve el permiso inicial. */
class NotificadorAndroid(private val context: Context) : Notificador {
 private var pendiente: Pair<String,String>? = null
 private var permisoResuelto = false
 init { context.getSystemService(NotificationManager::class.java).createNotificationChannel(NotificationChannel("sync", "Sincronización", NotificationManager.IMPORTANCE_DEFAULT)) }
 private fun permitido() = Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
 @Synchronized override fun mostrar(titulo: String, texto: String) {
  if(!permitido()) { if(!permisoResuelto) pendiente=titulo to texto; return }
  pendiente=null
  emitir(titulo,texto)
 }
 /** Lo llama MainActivity cuando la persona responde al permiso. */
 @Synchronized fun actualizarPermiso(concedido: Boolean) {
  permisoResuelto=true
  val aviso=pendiente
  pendiente=null
  if(concedido && permitido() && aviso!=null) emitir(aviso.first,aviso.second)
 }
 private fun emitir(titulo: String,texto: String) {
  val intent=Intent(context,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
  val abrir=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
  try { NotificationManagerCompat.from(context).notify(11,NotificationCompat.Builder(context,"sync").setSmallIcon(R.drawable.ic_notification).setContentTitle(titulo).setContentText(texto).setContentIntent(abrir).setAutoCancel(true).build()) } catch (_: SecurityException) { }
 }
}
