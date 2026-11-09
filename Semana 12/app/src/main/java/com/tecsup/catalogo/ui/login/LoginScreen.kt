package com.tecsup.catalogo.ui.login
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.ui.components.NexioLogo
import com.tecsup.catalogo.ui.theme.*
@Composable fun LoginScreen(vm: LoginViewModel, volver: ()->Unit = {}) {
 val estado by vm.estado.collectAsStateWithLifecycle()
 var correo by rememberSaveable { mutableStateOf("") };var clave by rememberSaveable { mutableStateOf("") };var visible by rememberSaveable {mutableStateOf(false)}
 Column(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(BerenjenaOscura,Morado))).safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(horizontal=28.dp),horizontalAlignment=Alignment.CenterHorizontally) {
  TextButton(onClick=volver,modifier=Modifier.align(Alignment.Start)) { Text("← Volver",color=Lavanda) }
  NexioLogo(Modifier.width(180.dp).height(134.dp))
  Spacer(Modifier.height(26.dp))
  Text("Bienvenida a NEXIO",color=Color.White,fontSize=26.sp,fontWeight=FontWeight.Bold)
  Text("Tu próxima tecnología te espera.",color=Lavanda,modifier=Modifier.padding(top=10.dp,bottom=26.dp))
  Card(shape=RoundedCornerShape(28.dp),colors=CardDefaults.cardColors(containerColor=Fondo)) {
   Column(Modifier.padding(22.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
    Text("Inicia sesión",fontWeight=FontWeight.Bold,fontSize=22.sp,color=Morado)
    OutlinedTextField(correo,{correo=it},label={Text("Correo electrónico")},singleLine=true,keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Email),shape=RoundedCornerShape(14.dp),modifier=Modifier.fillMaxWidth())
    OutlinedTextField(clave,{clave=it},label={Text("Contraseña")},singleLine=true,visualTransformation=if(visible) VisualTransformation.None else PasswordVisualTransformation(),trailingIcon={TextButton(onClick={visible=!visible}){Text(if(visible) "Ocultar" else "Ver",fontSize=12.sp)}},keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Password),shape=RoundedCornerShape(14.dp),modifier=Modifier.fillMaxWidth())
    if(estado is UiState.Error) Text((estado as UiState.Error).mensaje,color=MaterialTheme.colorScheme.error)
    Button(onClick={vm.ingresar(correo,clave)},enabled=estado !is UiState.Cargando,colors=ButtonDefaults.buttonColors(containerColor=Amarillo,contentColor=Morado),modifier=Modifier.fillMaxWidth().height(52.dp)) { Text(if(estado is UiState.Cargando) "Ingresando…" else "Ingresar  →",fontWeight=FontWeight.Bold,fontSize=16.sp) }
   }
  }
  Spacer(Modifier.height(28.dp))
 }
}
