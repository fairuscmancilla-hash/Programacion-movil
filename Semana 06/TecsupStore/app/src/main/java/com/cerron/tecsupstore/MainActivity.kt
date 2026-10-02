package com.cerron.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cerron.tecsupstore.screens.InicioScreen
import com.cerron.tecsupstore.ui.theme.TecsupstoreTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TecsupstoreTheme {
                InicioScreen()
            }
        }
    }
}