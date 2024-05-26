package com.example.marsphotos.BDLOCAL.REPO

import com.example.marsphotos.BDLOCAL.BD.Calificaciones
import com.example.marsphotos.BDLOCAL.BD.CargaAcademicaItem
import com.example.marsphotos.BDLOCAL.BD.EntityDetalles
import com.example.marsphotos.BDLOCAL.BD.FECHA
import kotlinx.coroutines.flow.Flow

interface REPOSUSPEND{
    //---------------------------------------------------------------------------------
    suspend fun InsertarDetalles(item: EntityDetalles)
    suspend fun ObtenerDetalles(ID:Int):EntityDetalles
    //---------------------------------------------------------------------------------
    suspend fun InsertarFechaRL(item: FECHA)
    suspend fun ObtenerFechaRL(ID:Int):FECHA
    //---------------------------------------------------------------------------------
    suspend fun InsertarCargaRL(item: CargaAcademicaItem)
    suspend fun ObtenerCargaRL(ID:Int): Flow<List<CargaAcademicaItem>>
//---------------------------------------------------------------------------------
    suspend fun InsertarCalificacionesRL(item: Calificaciones)
    suspend fun ObtenerCalificacionesRL(ID:Int): Flow<List<Calificaciones>>
}