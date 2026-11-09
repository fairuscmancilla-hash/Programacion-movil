package com.tecsup.catalogo
import android.app.Application
class NexioApplication : Application() {
 val container: AppContainer by lazy { AppContainer(applicationContext) }
}
