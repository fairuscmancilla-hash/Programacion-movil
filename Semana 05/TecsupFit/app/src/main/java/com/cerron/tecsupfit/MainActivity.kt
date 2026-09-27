package com.cerron.tecsupfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cerron.tecsupfit.navigation.AppNavigation
import com.cerron.tecsupfit.ui.theme.TecsupFitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TecsupFitTheme {

                AppNavigation()
            }
        }
    }
}

