package com.cerron.clinica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cerron.clinica.navigation.AppNavigation
import com.cerron.clinica.ui.theme.ClinicaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClinicaTheme {
                AppNavigation()
            }
        }
    }
}
