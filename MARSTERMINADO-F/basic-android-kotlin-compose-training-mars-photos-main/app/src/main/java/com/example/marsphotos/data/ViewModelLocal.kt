package com.example.marsphotos.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.marsphotos.BDLOCAL.BD.EntityDetalles
import com.example.marsphotos.BDLOCAL.BD.FECHA
import com.example.marsphotos.BDLOCAL.REPO.REPOSUSPEND
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.WORKS.SICE.WorkerLOGIN
import com.example.marsphotos.model.ALUMNO
import com.example.marsphotos.model.Calificaciones
import com.example.marsphotos.model.CargaAcademicaItem
import com.example.marsphotos.model.DETALLESALUMNO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class ViewModelLocal (val REPOLOCAL : REPOSUSPEND,
context: Context
) : ViewModel() {
    var UIDESTALLES by mutableStateOf(ALUMNO(1,"","","",""))
    var FechaActualVW by mutableStateOf("")
    var listaCarga = listOf<CargaAcademicaItem>()
    var listaCalificacion = listOf<Calificaciones>()

    val CON = context


    fun CalificacionesLOCAL(Lista : List<Calificaciones>)
    {
        listaCalificacion = Lista
        Log.d("calificaciones-LOCAL",Lista.toString())
    }
    fun ObtenerCalificaciones(id:Int)  : List<Calificaciones>
    {
        try {
            viewModelScope.launch {
                REPOLOCAL.ObtenerCalificacionesRL(id)
                    .map { items ->
                        items.map { it ->
                            Calificaciones(
                                it.Observaciones,
                                it.C13 ?: "null", it.C12 ?: "null", it.C11 ?: "null", it.C10 ?: "null",
                                it.C9 ?: "null", it.C8 ?: "null", it.C7 ?: "null", it.C6 ?: "null",
                                it.C5 ?: "null", it.C4 ?: "null", it.C3 ?: "null", it.C2 ?: "null",
                                it.C1 ?: "null", it.UnidadesActivas ?: "", it.Materia ?: "",
                                it.Grupo ?: "")
                        }
                    }
                    .collect { carga ->
                        //  cargaAcademica.clear()
                        Log.d("CALIFICACIONES OBTENIDAS",carga.toString())
                        CalificacionesLOCAL(carga)
                    }
            }
            Log.d("CARGA-LISTA","TERMINO----------------------------------")
        }catch (e: Exception) {
            Log.e("ERROR", "Error durante el proceso obtener detalles: ${e.message}", e)
        }
        return  listOf()
    }
    suspend fun guardarCalificaciones(List : List<Calificaciones>) {
        try {
            REPOLOCAL.ObtenerCalificacionesRL(1).map { cargaAcademicaList ->
                if (cargaAcademicaList.isEmpty()) {
                     List.forEach {
                         REPOLOCAL.InsertarCalificacionesRL(
                             com.example.marsphotos.BDLOCAL.BD.Calificaciones(
                                 null,1,it.Observaciones,it.C13,it.C12,it.C11,it.C10,it.C9,
                                 it.C8,it.C7,it.C6,it.C5,it.C4,it.C3,it.C2,it.C1,it.UnidadesActivas,
                                 it.Materia,it.Grupo
                         ))
                        Log.d("SE INSERTO EN LAS CALIFICACIONES", it.toString())
                    }
                } else {
                    Log.d("YA EXISTE LA CALIFICACION", "--------------------")
                }
            }.launchIn(viewModelScope) // viewModelScope es un scope de ViewModel
        } catch (e: Exception) {
            Log.e("ERROR", "Error durante el proceso guardar detalles: ${e.message}", e)
        }
    }
   suspend fun guardarCarga(List : List<CargaAcademicaItem>) {
        try {
            REPOLOCAL.ObtenerCargaRL(1).map { cargaAcademicaList ->
                if (cargaAcademicaList.isEmpty()) {
                    Log.d("CARGA-OBTENER CARGA", cargaAcademicaList.toString())
                    List.forEach {
                        REPOLOCAL.InsertarCargaRL(com.example.marsphotos.BDLOCAL.BD.CargaAcademicaItem(
                            null, 1, it.Semipresencial, it.Observaciones, it.Docente, it.clvOficial, it.Sabado, it.Viernes,
                            it.Jueves, it.Miercoles, it.Martes, it.Lunes, it.EstadoMateria, it.CreditosMateria, it.Materia,
                            it.Grupo
                        ))
                        Log.d("SE INSERTO EN LA CARGA", it.toString())
                    }
                } else {
                    Log.d("YA EXISTE LA CARGA", "--------------------")
                }
            }.launchIn(viewModelScope) // viewModelScope es un scope de ViewModel
        } catch (e: Exception) {
            Log.e("ERROR", "Error durante el proceso guardar detalles: ${e.message}", e)
        }
    }
    fun guardarDetalles(ALUMNO : ALUMNO) {
        runBlocking {
            launch(Dispatchers.IO) {
                try {
                    UIDESTALLES = ALUMNO
                    REPOLOCAL.InsertarDetalles(Modelo_Entidad(ALUMNO))
                   // Log.e("VIEWMODEL", "SE INSERTO EL ALUMNO" + Modelo_Entidad(ALUMNO).toString())
                }catch (e: Exception) {
                    Log.e("ERROR", "Error durante el proceso guardar detalles: ${e.message}", e)
                }
            }
        }
    }
     fun ObtenerDetalles(id:Int)  : ALUMNO
    {  runBlocking {
        launch(Dispatchers.IO) {
                 try {
                     UIDESTALLES = Entidad_Modelo(REPOLOCAL.ObtenerDetalles(id))
                    //  Toast.makeText(CON, "SE INSERTO: " + UIDESTALLES.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("SE OBTUVO",UIDESTALLES.toString())
                 }catch (e: Exception) {
                     Log.e("ERROR", "Error durante el proceso obtener detalles: ${e.message}", e)
                 }}}
        return UIDESTALLES
    }
    fun guardarFecha(FechaActual: String) {
        runBlocking {
            launch(Dispatchers.IO) {
                try {
                    REPOLOCAL.InsertarFechaRL(FECHA(1 ,FechaActual))
                }catch (e: Exception) {
                    Log.e("ERROR", "Error durante el proceso guardar detalles: ${e.message}", e)
                }
            }
        }

    }
    fun ObtenerFecha(id:Int)  : String
    {  runBlocking {
        launch(Dispatchers.IO) {
            try {
                FechaActualVW = REPOLOCAL.ObtenerFechaRL(id).Fecha.toString()
                Log.d("FECHA RECUPERADA",FechaActualVW.toString())

            }catch (e: Exception) {
                Log.e("ERROR", "Error durante el proceso obtener detalles: ${e.message}", e)
            }}}
        return FechaActualVW
    }
    fun CARGALOCAL(Lista : List<CargaAcademicaItem>)
    {
        listaCarga = Lista
        Log.d("CARGA-LOCAL",Lista.toString())
    }

    fun ObtenerCarga(id:Int)  : List<CargaAcademicaItem>
    {
        try {
            // var cargaAcademica: MutableList<CargaAcademicaItem> = mutableListOf()
            viewModelScope.launch {
                REPOLOCAL.ObtenerCargaRL(id)
                    .map { items ->
                        items.map { item ->
                            CargaAcademicaItem(
                                item.Semipresencial, item.Observaciones, item.Docente,
                                item.clvOficial, item.Sabado, item.Viernes,
                                item.Jueves, item.Miercoles, item.Martes,
                                item.Lunes, item.EstadoMateria, item.CreditosMateria,
                                item.Materia, item.Grupo)
                        }
                    }
                    .collect { carga ->
                        //  cargaAcademica.clear()
                        Log.d("CARGA-LISTA",carga.toString())
                        CARGALOCAL(carga)
                    }
            }
            Log.d("CARGA-LISTA","TERMINO----------------------------------")
        }catch (e: Exception) {
            Log.e("ERROR", "Error durante el proceso obtener detalles: ${e.message}", e)
        }
        return  listOf()
    }
    fun Modelo_Entidad(ALUMNO : ALUMNO) : EntityDetalles
    {
        var Det = EntityDetalles(1,"","","","")
        Det.Ncontrol = ALUMNO.matricula
        Det.Contraseña = ALUMNO.contrasenia
        Det.Estado = ALUMNO.estatus
        Det.Acceso = ALUMNO.acceso
        return Det
    }
    fun Entidad_Modelo(ALUMNO : EntityDetalles) : ALUMNO
    {
        var Det = ALUMNO(1,"","","","")
        Det.matricula = ALUMNO.Ncontrol
        Det.contrasenia = ALUMNO.Contraseña.toString()
        Det.estatus = ALUMNO.Estado
        Det.acceso = ALUMNO.Acceso
        return Det
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MarsPhotosApplication)
                val R = app.container2.REPOLOCAL
                ViewModelLocal(R,this.MarsPhotosApplication().applicationContext)
            }
        }
    }
}