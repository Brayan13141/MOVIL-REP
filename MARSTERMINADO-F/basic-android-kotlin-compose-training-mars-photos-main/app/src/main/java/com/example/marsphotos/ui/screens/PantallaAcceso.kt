package com.example.marsphotos.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.marsphotos.R
import com.example.marsphotos.data.VIEWLOGIN
import com.example.marsphotos.data.ViewModelLocal
import com.example.marsphotos.model.ALUMNO
import com.example.marsphotos.ui.Nav.PantallasNav
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

@Composable
fun PantallaSesion(
    viewModel2: ViewModelLocal = viewModel(factory = ViewModelLocal.Factory),
    viewModel: VIEWLOGIN = viewModel(factory = VIEWLOGIN.Factory), modifier: Modifier = Modifier,
                   navController: NavController,
    context: Context
) {
    var bandera = 0
    var alumno = ALUMNO(1,"","","","")
    var alumno2 = ALUMNO(1,"","","","")
    var fechaUactual = ""
if(viewModel.bandera==1)
{
    alumno = viewModel.AL
    viewModel2.guardarDetalles(alumno)
    viewModel2.guardarFecha(LocalDate.now().toString())
    viewModel.bandera=1
}else{
    alumno2 = viewModel2.ObtenerDetalles(1)
    fechaUactual = viewModel2.ObtenerFecha(1)
    viewModel.bandera=2
   // Log.d("ALUMNO BD", alumno2.toString() + " BANDERA "+bandera.toString())
}
    val Rutina = rememberCoroutineScope()
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp) // Añadir paddings
                .align(Alignment.TopStart) // Posicionar a la izquierda y arriba
        ) {

        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.size(30.dp))
            Image(
                painter = painterResource(id = R.drawable.alumno),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
                //.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Detalles del Alumno",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier//.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if(alumno.matricula != "" && viewModel.bandera==1 || alumno2.matricula != "" && viewModel.bandera==2)
        {
            item {
                if(alumno2.matricula!="")
                {
                   Text(text = "ULTIMA ACTUAILIZACIÓN: "+ fechaUactual)
                }
                CampoDetalle(icon = Icons.Default.Person, label = "Matrícula", value = if (alumno.matricula != "") alumno.matricula else  if (alumno2.matricula!="") alumno2.matricula else "NO JALO")
                CampoDetalle(icon = Icons.Default.Lock, label = "Contraseña", value = if (alumno.contrasenia != "") alumno.contrasenia else if (alumno2.contrasenia!="") alumno2.contrasenia else "NO JALO")
                CampoDetalle(icon = Icons.Default.Warning, label = "Estatus", value =if (alumno.estatus != "") alumno.estatus else if (alumno2.estatus!="") alumno2.estatus else "NO JALO")
                CampoDetalle(icon = Icons.Default.Check, label = "Acceso", value = if (alumno.acceso != "") alumno.acceso else if (alumno2.acceso!="") alumno2.acceso else "NO JALO")
                Row {
                    Button(onClick = {
                            if(viewModel.isInternetAvailable(context)==true){
                                viewModel.OBTENERCARGA()
                                navController.navigate(PantallasNav.CARGA.route)
                                Toast.makeText(context,"SE INICIO CON DATOS DEL SERVIDOR",Toast.LENGTH_SHORT).show()
                            }else
                            {
                                viewModel2.ObtenerCarga(1)
                                    Log.d("OBTUVO LA CARGA","PANTALLA ACCESO")
                                    navController.navigate(PantallasNav.CARGA.route)
                                    Toast.makeText(context,"SE INICIO CON DATOS DE LA BD",Toast.LENGTH_SHORT).show()
                                }
                         }
                    ) {
                        Text(text = "CARGA")
                    }
                    Button(onClick = {
                        if(viewModel.isInternetAvailable(context)==true){
                            viewModel.OBTENERCALIFICACIONES()
                            navController.navigate(PantallasNav.CALI.route)
                            Toast.makeText(context,"SE INICIO CON DATOS DEL SERVIDOR",Toast.LENGTH_SHORT).show()
                        }else
                        {
                            viewModel2.ObtenerCalificaciones(1)
                            Log.d("OBTUVO LAS CALIFICACIONES","PANTALLA ACCESO")
                            navController.navigate(PantallasNav.CALI.route)
                            Toast.makeText(context,"SE INICIO CON DATOS DE LA BD",Toast.LENGTH_SHORT).show()
                        }

                         }) {
                        Text(text = "CALIFICACIÓN")
                    }
                }
            }
        }
        item {Text(text = alumno2.toString())}
    }
}

@Composable
private fun CampoDetalle(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
