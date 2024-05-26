package com.example.regreso_casa

import PermissionBox
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import kotlinx.coroutines.tasks.await
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.regreso_casa.INTERFAZ.API
import com.example.regreso_casa.INTERFAZ.Response
import com.google.android.gms.location.LocationServices

import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker



import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

import com.google.maps.android.compose.rememberCameraPositionState

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("MissingPermission")
@Composable
fun MiMapa(){
    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first()),
        onGranted = {
            MiMapaContent(it.contains(Manifest.permission.ACCESS_FINE_LOCATION))
        }
    )
}

@RequiresPermission(
    anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ]
)
@Composable
fun MiMapaContent(usePreciseLocation: Boolean) {
    var myCurrentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var newLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var mapClick by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var polilyne by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var solicitudRuta by remember { mutableIntStateOf(0) }
    var context = LocalContext.current
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.HYBRID)) }

    val home = LatLng(20.118465607621353, -101.19565639408108)


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(home, 15f)
    }

    Box(Modifier.fillMaxSize()) {
        Toast.makeText(context, "SE CREO EL MAPA", Toast.LENGTH_SHORT).show();
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapClick = {
                mapClick= LatLng(it.latitude,it.longitude)
                newLocation=mapClick
            }
        ) {

            Polyline(points = polilyne)
            Marker(
                state = MarkerState(position = home),
                title = "Home",
                snippet = "Marker in Home"
            )
            Marker(
                state = MarkerState(position = mapClick),
                title = "UBICACION NUEVA",
                snippet = ""
            )
            // Agregar marcador en myCurrentLocation
            if (myCurrentLocation != LatLng(0.0, 0.0)) {
                Marker(
                    state = MarkerState(position = myCurrentLocation),
                    title = "Current Location",
                    snippet = "Marker in Current Location"
                )
            }
        }

        Column(){
            val scope = rememberCoroutineScope()
            val locationClient = remember {
                LocationServices.getFusedLocationProviderClient(context)
            }
            var locationInfo by remember {
                mutableStateOf("")
            }
            Button(
                onClick = {
                    try {
                        scope.launch{
                            Log.d("ENTRO","----BUTTON---------------")
                            val priority = if (usePreciseLocation) {
                                Priority.PRIORITY_HIGH_ACCURACY
                            } else {
                                Priority.PRIORITY_BALANCED_POWER_ACCURACY
                            }
                            val result = locationClient.getCurrentLocation(
                                priority,
                                CancellationTokenSource().token,
                            ).await()
                            result?.let { fetchedLocation ->
                                Log.d("RESULTADO",result.toString())
                                locationInfo =
                                    "Current location is \n" + "lat : ${fetchedLocation.latitude}\n" +
                                            "long : ${fetchedLocation.longitude}\n" + "fetched at ${System.currentTimeMillis()}"
                                myCurrentLocation= LatLng(fetchedLocation.latitude,fetchedLocation.longitude)
                            }
                            Toast.makeText(context, "UBICACION EXITOSA", Toast.LENGTH_SHORT).show();
                        }
                    }catch (e: Exception)
                    {
                        Toast.makeText(context, "UBICACION NO EXITOSA", Toast.LENGTH_SHORT).show();
                    }
                },
            ) {
                Text(text = "Get current location")
            }
            Button(
                onClick = {
                    solicitudRuta=1
                }
            ){
                Text("Calcular Ruta")
            }
            if(newLocation.latitude.toString()!="0.0")
            {
                Button(
                    onClick = {
                        if(newLocation.latitude.toString()=="0.0")
                        {
                            Toast.makeText(context, "SELECCIONE UNA UBICACIÓN", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            solicitudRuta=2
                        }

                    }
                ){
                    Text("CALCULAR RUTA ALTERNA  ")
                }
            }
            Row{
                Text(text = "ZOOM")
                Switch(
                    checked = uiSettings.zoomControlsEnabled,
                    onCheckedChange = {
                        uiSettings = uiSettings.copy(zoomControlsEnabled = it)
                    }
                )
            }
         
        }
      
    }
    if (solicitudRuta == 1) {
        trazarRuta(context,home, myCurrentLocation) { puntos ->
            polilyne = puntos
        }
    }else if(solicitudRuta==2)
    {
        trazarRuta(context,home, newLocation) { puntos ->
            polilyne = puntos
        }
    }

    if (polilyne.isNotEmpty()) {
        Log.d("entro","polyline")
    }
}

fun trazarRuta(cont:Context,start: LatLng, end: LatLng, callback: (List<LatLng>) -> Unit ) {
    Log.d("ENTRO","-----TRAZAR RUTA--------------")
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openrouteservice.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        val response = retrofit.create(API::class.java)
            .getRoute(
                "5b3ce3597851110001cf6248b49ce2d3c3f84d06aabd7a8a903b5a85",
                "${start.longitude},${start.latitude}",
                "${end.longitude},${end.latitude}"
            )
        if (response.isSuccessful) {
            var puntosRuta = calcularPuntos(response.body())
            CoroutineScope(Dispatchers.Main).launch {
                // Código que quieres ejecutar en el hilo principal
                Toast.makeText(cont,"RUTA TRAZADA", Toast.LENGTH_SHORT).show()
            }
            callback(puntosRuta)
        } else {
            Log.d("FALLO EN LA RUTA","")
        }
    }
}

fun calcularPuntos(ruta: Response?): List<LatLng> {
    val puntos = mutableListOf<LatLng>()
    ruta?.features?.firstOrNull()?.geometry?.coordinates?.forEach {
        val punto = LatLng(it[1], it[0])
        puntos.add(punto)
    }
    return puntos
}
