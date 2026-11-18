package com.cerron.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.cerron.campusgps.data.*
import com.cerron.campusgps.viewmodel.*
import com.cerron.campusgps.ui.PantallaMapa
import com.cerron.campusgps.ui.theme.CampusGPSTheme
class MainActivity : ComponentActivity() {
 private val vm: MapaViewModel by viewModels { CampusViewModelFactory(UbicacionRepositoryGoogle(applicationContext), LugaresRepositoryLocal(), UltimoLugarRepositoryDataStore(applicationContext)) }
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent { CampusGPSTheme { PantallaMapa(vm) } }
 }
}
