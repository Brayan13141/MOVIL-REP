package com.example.marsphotos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.marsphotos.data.OBJ
import com.example.marsphotos.data.VIEWLOGIN

@Composable
fun PantallaCalificaciones(
    viewModel: VIEWLOGIN = viewModel(factory = VIEWLOGIN.Factory),
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(viewModel.listaCalificacion.size) { index ->
            val item = viewModel.listaCalificacion[index]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Materia: ${item.Materia}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Calificaciones:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                CalificacionItem("U1", item.C1?.toInt())
                CalificacionItem("U2", item.C2?.toInt())
                CalificacionItem("U3", item.C3?.toInt())
                CalificacionItem("U4", item.C4?.toInt())
                CalificacionItem("U5", item.C5?.toInt())
                CalificacionItem("U6", item.C6?.toInt())
                CalificacionItem("U7", item.C7?.toInt())
                CalificacionItem("U8", item.C8?.toInt())
                CalificacionItem("U9", item.C9?.toInt())
                CalificacionItem("U10", item.C10?.toInt())
                CalificacionItem("U11", item.C11?.toInt())
                CalificacionItem("U12", item.C12?.toInt())
                CalificacionItem("U13", item.C13?.toInt())
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun CalificacionItem(nombre: String, calificacion: Int?) {
    if (calificacion != null) {
        Text(
            text = "$nombre: $calificacion",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

