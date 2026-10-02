package com.cerron.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import com.cerron.tecsupstore.components.AppDrawer
import com.cerron.tecsupstore.screens.InicioScreen
import com.cerron.tecsupstore.ui.theme.TecsupstoreTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TecsupstoreTheme {

                val drawerState = rememberDrawerState(
                    initialValue = DrawerValue.Closed
                )

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer()
                    }
                ) {

                    InicioScreen()
                }
            }
        }
    }
}