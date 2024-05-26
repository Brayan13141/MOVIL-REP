package com.example.marsphotos.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.marsphotos.R
import com.example.marsphotos.data.VIEWLOGIN
import com.example.marsphotos.ui.Nav.PantallasNav
import kotlinx.coroutines.launch

@Composable
fun PantallaInicio(
    viewModel: VIEWLOGIN = viewModel(factory = VIEWLOGIN.Factory),
    navController: NavController,
    context: Context
) {
    var showDialog by remember { mutableStateOf(false) }
    var offlineSession by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.size(100.dp))

        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = "ITSUR"
        )

        TextField(
            value = viewModel.Ncontrol,
            onValueChange = { viewModel.fNcontrol(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextField(
            value = viewModel.Contraseña,
            onValueChange = { viewModel.fContraseña(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        val coroutineScope = rememberCoroutineScope()

        BotonIngresar {
            coroutineScope.launch {
                if(viewModel.isInternetAvailable(context)==true){
                    if (viewModel.ingresar(viewModel.Ncontrol, viewModel.Contraseña)==true) {
                        viewModel.bandera=1
                        navController.navigate(PantallasNav.SESION.route)
                    } else {
                        showDialog = true
                    }
                }else
                {
                    navController.navigate(PantallasNav.SESION.route)
                    viewModel.bandera=2
                    Toast.makeText(context,"NO HAY CONEXION",Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}



@Composable
private fun BotonIngresar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text("INGRESAR")
    }
}
