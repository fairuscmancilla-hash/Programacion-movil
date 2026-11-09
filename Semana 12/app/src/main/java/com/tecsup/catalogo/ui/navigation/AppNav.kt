package com.tecsup.catalogo.ui.navigation
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.tecsup.catalogo.AppContainer
import com.tecsup.catalogo.domain.model.Rol
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.ui.login.*
import com.tecsup.catalogo.ui.lista.*
import com.tecsup.catalogo.ui.favoritos.*
import com.tecsup.catalogo.ui.detalle.*
import com.tecsup.catalogo.ui.admin.*
import com.tecsup.catalogo.ui.components.Cargando
import com.tecsup.catalogo.ui.theme.*
@Composable fun AppNav(container: AppContainer, permisoNotificaciones: (Boolean) -> Unit = {}) {
 val login: LoginViewModel=viewModel(factory=container.factory())
 val estado by login.estado.collectAsStateWithLifecycle()
 val ingreso by login.mostrarIngreso.collectAsStateWithLifecycle()
 val sesion=(estado as? UiState.Exito)?.datos
 if(sesion==null) {
  if(estado is UiState.Cargando && !ingreso) {Box(Modifier.fillMaxSize().background(Morado)){Cargando()};return}
  if(ingreso) LoginScreen(login,login::verBienvenida) else WelcomeScreen(login::comenzar)
  return
 }
 key(sesion.uid) {
  val nav=rememberNavController()
  val listaVm: ListaViewModel=viewModel(key="lista-${sesion.uid}",factory=container.factory())
  // Si se niega el permiso, la app sigue igual, solo sin avisos.
  val permiso=rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission(),permisoNotificaciones)
  LaunchedEffect(Unit){if(Build.VERSION.SDK_INT>=33)permiso.launch(Manifest.permission.POST_NOTIFICATIONS) else permisoNotificaciones(true)}
  val entrada by nav.currentBackStackEntryAsState();val ruta=entrada?.destination?.route
  Scaffold(containerColor=Fondo,topBar={
   Row(Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(BerenjenaOscura,Berenjena))).statusBarsPadding().padding(horizontal=18.dp,vertical=18.dp),verticalAlignment=Alignment.CenterVertically) {
    Box(Modifier.size(44.dp).clip(CircleShape).background(Lavanda),contentAlignment=Alignment.Center){Icon(Icons.Outlined.Person,null,tint=Morado)}
    Column(Modifier.weight(1f).padding(start=12.dp)){Text("Hola, ${sesion.correo.substringBefore('@').replaceFirstChar{it.uppercase()}} 👋",fontSize=18.sp,fontWeight=FontWeight.Bold,color=Color.White,maxLines=1);Text("¿Qué tecnología buscas hoy?",fontSize=11.sp,color=Lavanda)}
    IconButton(onClick={listaVm.refrescar()}){Icon(Icons.Outlined.Refresh,"Actualizar catálogo",tint=Color.White)}
   }
  },bottomBar={
   val tabs=listOf(Triple("lista","Inicio",Icons.Outlined.Home),Triple("categorias","Categorías",Icons.Outlined.GridView),Triple("favoritos","Favoritos",Icons.Outlined.FavoriteBorder))+ (if(sesion.rol==Rol.ADMIN)listOf(Triple("admin","Admin",Icons.Outlined.Inventory2))else emptyList())+listOf(Triple("perfil","Perfil",Icons.Outlined.PersonOutline))
   Row(Modifier.fillMaxWidth().background(Morado,RoundedCornerShape(topStart=24.dp,topEnd=24.dp)).navigationBarsPadding().padding(horizontal=12.dp,vertical=10.dp),horizontalArrangement=Arrangement.SpaceAround) {
    tabs.forEach{(r,t,i)->val seleccionado=ruta==r
     Column(Modifier.clip(RoundedCornerShape(18.dp)).background(if(seleccionado)Amarillo else Color.Transparent).clickable{nav.navigate(r){popUpTo("lista"){saveState=true};launchSingleTop=true;restoreState=true}}.padding(horizontal=12.dp,vertical=8.dp),horizontalAlignment=Alignment.CenterHorizontally){Icon(i,t,tint=if(seleccionado)Morado else Color.White,modifier=Modifier.size(23.dp));Text(t,fontSize=9.sp,color=if(seleccionado)Morado else Color.White,modifier=Modifier.padding(top=3.dp))}
    }
   }
  }){padding->Box(Modifier.fillMaxSize().padding(padding).background(Brush.verticalGradient(listOf(Lavanda,Fondo)))) {
   NavHost(nav,startDestination="lista") {
    composable("lista"){ListaScreen(listaVm,{nav.navigate("detalle/$it")})}
    composable("categorias"){ListaScreen(listaVm,{nav.navigate("detalle/$it")},true)}
    composable("favoritos"){val vm:FavoritosViewModel=viewModel(factory=container.factory());FavoritosScreen(vm){nav.navigate("detalle/$it")}}
    composable("detalle/{id}"){e->val id=e.arguments?.getString("id")?.toIntOrNull()?:return@composable;val vm:DetalleViewModel=viewModel(factory=container.factory(id));DetalleScreen(vm){nav.popBackStack()}}
    composable("admin"){if(sesion.rol==Rol.ADMIN){val vm:AdminViewModel=viewModel(factory=container.factory());AdminScreen(vm)}}
    composable("perfil"){Column(Modifier.fillMaxSize().padding(28.dp),verticalArrangement=Arrangement.spacedBy(20.dp)){Text("Mi perfil",fontSize=28.sp,fontWeight=FontWeight.Bold);Text(sesion.correo);Text(if(sesion.rol==Rol.ADMIN)"Administrador" else "Usuario");Button(onClick={login.cerrarSesion()}){Text("Cerrar sesión")}}}
   }
  }}
 }
}
