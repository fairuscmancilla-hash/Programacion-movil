package com.tecsup.catalogo.data.local
import android.content.Context
/** Solo guarda si ya se pasó la bienvenida; nunca almacena contraseñas. */
class PreferenciasInicio(context:Context):InicioRepository {
 private val prefs=context.getSharedPreferences("nexio_inicio",Context.MODE_PRIVATE)
 override fun ingresoHabilitado()=prefs.getBoolean("ingreso_habilitado",false)
 override fun habilitarIngreso(){prefs.edit().putBoolean("ingreso_habilitado",true).apply()}
}
