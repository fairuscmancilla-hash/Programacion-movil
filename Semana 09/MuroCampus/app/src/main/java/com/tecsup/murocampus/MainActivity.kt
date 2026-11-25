package com.tecsup.murocampus

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tecsup.murocampus.ui.theme.MuroCampusTheme

// [E] Proyecto base: solo abre la app con el tema. Las pantallas se agregan en los pasos 6 a 9.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            MuroCampusTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("MuroCampus", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}
