package com.example.marsphotos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.marsphotos.data.VIEWLOGIN
import com.example.marsphotos.data.ViewModelLocal
import com.example.marsphotos.model.CargaAcademicaItem
@Composable
fun CargaAcademicaList(
    viewModel: VIEWLOGIN = viewModel(factory = VIEWLOGIN.Factory),
    viewModelLocal : ViewModelLocal = viewModel(factory = VIEWLOGIN.Factory),
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(if (viewModel.listaCarga.isNotEmpty() && viewModel.bandera==1) viewModel.listaCarga.size else
            viewModelLocal.listaCarga.size
            ) { index ->
                val item = if (viewModel.listaCarga.isNotEmpty() && viewModel.bandera==1) viewModel.listaCarga.get(index) else
                viewModelLocal.listaCarga.get(index)
                if (item != null) {
                    TarjetaCargaAcademica(item = item) {
                    }
                }
            }
            item {
                 if (viewModel.listaCarga?.isEmpty() == true) {
                    Text(
                        text = "No hay carga académica disponible",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaCargaAcademica(item:CargaAcademicaItem, onClick: () -> Unit) {
    var mostrarDetalles by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = { mostrarDetalles = !mostrarDetalles }),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.Materia,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Grupo: ${item.Grupo}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Docente: ${item.Docente}", style = MaterialTheme.typography.bodyMedium)

            // Mostrar detalles solo si el estado es verdadero
            if (mostrarDetalles) {
                Text(
                    text = "Lunes: ${item.Lunes}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Martes: ${item.Martes}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Miércoles: ${item.Miercoles}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Jueves: ${item.Jueves}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Viernes: ${item.Viernes}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
