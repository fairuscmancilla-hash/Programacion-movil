package com.tecsup.catalogo.ui.lista
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import coil.compose.AsyncImage
import com.tecsup.catalogo.domain.model.Item
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.ui.components.*
import com.tecsup.catalogo.ui.theme.*
import java.util.Locale
fun precio(valor: Double) = com.tecsup.catalogo.domain.model.Moneda.formatearSoles(valor)
@Composable fun ListaScreen(vm: ListaViewModel, abrir: (Int)->Unit, categorias: Boolean=false) {
 val estado by vm.estado.collectAsStateWithLifecycle();val aviso by vm.aviso.collectAsStateWithLifecycle()
 Column(Modifier.fillMaxSize()) {
  aviso?.let { Text(it,color=Morado,fontSize=12.sp,modifier=Modifier.fillMaxWidth().background(Amarillo.copy(alpha=.4f)).padding(12.dp)) }
  when(val s=estado) { UiState.Cargando -> Cargando();is UiState.Error -> ErrorView(s.mensaje,vm::refrescar);is UiState.Exito -> Catalogo(s.datos,abrir,vm::alternarFavorito,true,vm::refrescar,categorias) }
 }
}
@Composable fun Catalogo(items: List<Item>, abrir: (Int)->Unit, favorito: (Int)->Unit, banner: Boolean=false, actualizar: ()->Unit={}, categorias: Boolean=false, mensajeVacio: String="Aún no hay productos aquí.") {
 var buscar by rememberSaveable {mutableStateOf("")};var marca by rememberSaveable {mutableStateOf("Todos")}
 val filtrados=items.filter { (it.titulo.contains(buscar,true)||it.marca.contains(buscar,true)) && (marca=="Todos" || it.marca==marca) }
 LazyVerticalGrid(columns=GridCells.Fixed(2),contentPadding=PaddingValues(start=18.dp,end=18.dp,bottom=22.dp),horizontalArrangement=Arrangement.spacedBy(12.dp),verticalArrangement=Arrangement.spacedBy(14.dp)) {
  item(span={GridItemSpan(maxLineSpan)}) { OutlinedTextField(buscar,{buscar=it},placeholder={Text("Buscar productos…",fontSize=13.sp)},leadingIcon={Icon(Icons.Outlined.Search,null)},trailingIcon={Icon(Icons.Outlined.Tune,"Buscar por nombre o marca")},singleLine=true,shape=RoundedCornerShape(18.dp),colors=OutlinedTextFieldDefaults.colors(unfocusedContainerColor=Color.White,focusedContainerColor=Color.White,unfocusedBorderColor=Color.Transparent,focusedBorderColor=Berenjena),modifier=Modifier.fillMaxWidth()) }
  if(banner && !categorias) {
   item(span={GridItemSpan(maxLineSpan)}) {
    val destacado=items.firstOrNull {it.titulo.contains("iPhone 13",true)} ?: items.firstOrNull()
    Card(onClick={destacado?.let{abrir(it.id)}},shape=RoundedCornerShape(24.dp),modifier=Modifier.fillMaxWidth()) {
     Row(Modifier.fillMaxWidth().height(180.dp).background(Brush.horizontalGradient(listOf(Berenjena,Lavanda))),verticalAlignment=Alignment.CenterVertically) {
      Column(Modifier.weight(1f).padding(start=20.dp,top=16.dp,bottom=16.dp)) {
       Text("Destacado",fontSize=10.sp,color=Morado,fontWeight=FontWeight.Bold,modifier=Modifier.background(Amarillo,RoundedCornerShape(20.dp)).padding(horizontal=10.dp,vertical=4.dp))
       Text(destacado?.titulo ?: "Tecnología NEXIO",color=Color.White,fontSize=22.sp,lineHeight=24.sp,fontWeight=FontWeight.Bold,modifier=Modifier.padding(top=8.dp))
       Text("Diseño y tecnología\npara tu día a día.",color=Lavanda,fontSize=11.sp,lineHeight=14.sp,modifier=Modifier.padding(top=6.dp))
       Text("Ver ahora  →",color=Morado,fontSize=11.sp,fontWeight=FontWeight.Bold,modifier=Modifier.padding(top=10.dp).background(Fondo,RoundedCornerShape(18.dp)).padding(horizontal=14.dp,vertical=6.dp))
      }
      AsyncImage(destacado?.imagenUrl,contentDescription=destacado?.titulo,modifier=Modifier.weight(1f).fillMaxHeight().padding(4.dp))
     }
    }
   }
   item(span={GridItemSpan(maxLineSpan)}) { Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.Center,verticalAlignment=Alignment.CenterVertically) { repeat(3) {i->Box(Modifier.padding(horizontal=4.dp).size(if(i==0) 8.dp else 6.dp).clip(CircleShape).background(if(i==0) Amarillo else Lavanda))} } }
  }
  if(banner) item(span={GridItemSpan(maxLineSpan)}) {
   Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(16.dp)) {
    (listOf("Todos")+items.map{it.marca}.distinct()).forEachIndexed {i,m->
     Column(Modifier.width(56.dp).clickable{marca=m},horizontalAlignment=Alignment.CenterHorizontally) {
      Box(Modifier.size(52.dp).background(if(marca==m) Lavanda else Lavanda.copy(alpha=.45f),RoundedCornerShape(17.dp)),contentAlignment=Alignment.Center) { Icon(when(i%4){0->Icons.Outlined.Smartphone;1->Icons.Outlined.PhoneIphone;2->Icons.Outlined.Devices;else->Icons.Outlined.Apps},null,tint=Morado,modifier=Modifier.size(26.dp)) }
      Text(if(m=="Todos") "Celulares" else m,fontSize=10.sp,maxLines=1,modifier=Modifier.padding(top=7.dp),color=Morado)
     }
    }
   }
  }
  item(span={GridItemSpan(maxLineSpan)}) { Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) {Text(if(!banner) "Tus favoritos" else if(categorias) "Explora por marca" else "Descubre tu próximo celular",fontWeight=FontWeight.Bold,fontSize=17.sp,color=Berenjena);if(banner) TextButton(onClick=actualizar,contentPadding=PaddingValues(0.dp)){Text("Actualizar ↻",fontSize=11.sp)} } }
  if(filtrados.isEmpty()) item(span={GridItemSpan(maxLineSpan)}) {Text(if(items.isEmpty()) mensajeVacio else "No hay resultados para tu búsqueda.",modifier=Modifier.fillMaxWidth().padding(24.dp),textAlign=androidx.compose.ui.text.style.TextAlign.Center,color=Morado)}
  items(filtrados,key={it.id}) {p->
   Card(onClick={abrir(p.id)},shape=RoundedCornerShape(20.dp),colors=CardDefaults.cardColors(containerColor=Superficie)) {
    Column(Modifier.padding(12.dp)) {
     Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) {
      Text("★ ${p.rating}",fontSize=10.sp,color=Morado,modifier=Modifier.background(Amarillo,RoundedCornerShape(12.dp)).padding(horizontal=8.dp,vertical=3.dp))
      IconButton(onClick={favorito(p.id)},modifier=Modifier.size(30.dp)) {Icon(if(p.esFavorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,if(p.esFavorito) "Quitar favorito" else "Guardar favorito",tint=Morado,modifier=Modifier.size(20.dp))}
     }
     AsyncImage(p.imagenUrl,contentDescription=p.titulo,modifier=Modifier.fillMaxWidth().height(122.dp))
     Text(p.titulo,fontSize=13.sp,fontWeight=FontWeight.SemiBold,minLines=2,maxLines=2,color=Berenjena)
     Text(precio(p.precio),fontSize=15.sp,fontWeight=FontWeight.Bold,color=Morado,modifier=Modifier.padding(top=6.dp))
    }
   }
  }
 }
}
