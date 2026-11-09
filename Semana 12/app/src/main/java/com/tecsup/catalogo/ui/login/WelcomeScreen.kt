package com.tecsup.catalogo.ui.login
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.tecsup.catalogo.R
import com.tecsup.catalogo.ui.components.NexioLogo
import com.tecsup.catalogo.ui.theme.*
@Composable fun WelcomeScreen(comenzar: ()->Unit) {
 BoxWithConstraints(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(BerenjenaOscura,Morado,Berenjena)))) {
  val alturaDisponible=maxHeight
  val compacta=alturaDisponible<720.dp
  Column(Modifier.fillMaxSize().safeDrawingPadding().verticalScroll(rememberScrollState()),horizontalAlignment=Alignment.CenterHorizontally) {
   Spacer(Modifier.height(12.dp))
   NexioLogo(Modifier.width(if(compacta) 180.dp else 210.dp).height((alturaDisponible * .18f).coerceAtMost(154.dp)))
   Image(painterResource(R.drawable.nexio_hero),contentDescription="Laptop, audífonos, celular y accesorios NEXIO sobre pedestales violetas",contentScale=ContentScale.FillWidth,modifier=Modifier.fillMaxWidth().height((alturaDisponible * .35f).coerceAtMost(300.dp)))
   Column(Modifier.fillMaxWidth().padding(horizontal=30.dp)) {
    Text("Tecnología\npara tu día a día",color=Color.White,fontSize=29.sp,lineHeight=33.sp,fontWeight=FontWeight.Bold)
    Spacer(Modifier.height(12.dp))
    Text("Descubre los mejores dispositivos, accesorios y más, en un solo lugar.",color=Lavanda,fontSize=15.sp,lineHeight=22.sp)
    Spacer(Modifier.height(26.dp))
    Button(onClick=comenzar,shape=RoundedCornerShape(50),colors=ButtonDefaults.buttonColors(containerColor=Amarillo,contentColor=Morado),modifier=Modifier.fillMaxWidth().height(54.dp)) { Text("Comenzar",fontSize=17.sp,fontWeight=FontWeight.Bold);Spacer(Modifier.width(18.dp));Icon(Icons.AutoMirrored.Outlined.ArrowForward,null) }
   }
   Row(Modifier.padding(vertical=22.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)) { repeat(3) { i-> Box(Modifier.size(if(i==0) 9.dp else 7.dp).clip(CircleShape).background(if(i==0) Amarillo else Lavanda.copy(alpha=.5f))) } }
  }
 }
}

