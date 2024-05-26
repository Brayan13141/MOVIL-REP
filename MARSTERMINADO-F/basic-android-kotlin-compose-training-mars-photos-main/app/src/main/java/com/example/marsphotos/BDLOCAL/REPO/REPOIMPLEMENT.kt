package com.example.marsphotos.BDLOCAL.REPO

import com.example.marsphotos.BDLOCAL.BD.Calificaciones
import com.example.marsphotos.BDLOCAL.BD.CargaAcademicaItem
import com.example.marsphotos.BDLOCAL.BD.DAO
import com.example.marsphotos.BDLOCAL.BD.EntityDetalles
import com.example.marsphotos.BDLOCAL.BD.FECHA
import kotlinx.coroutines.flow.Flow

class REPOIMPLEMENT (private val DAO: DAO): REPOSUSPEND{
    //-------------------------------------------------------------------------------------------------------
    override suspend fun InsertarDetalles(item: EntityDetalles) = DAO.InsertarDetalles(item)

    override suspend fun ObtenerDetalles(ID:Int): EntityDetalles = DAO.ObtenerDetalles(ID)
//-------------------------------------------------------------------------------------------------------
    override suspend fun InsertarFechaRL(item: FECHA)  = DAO.InsertarFecha(item)
    override suspend fun ObtenerFechaRL(ID: Int) = DAO.ObtenerFecha(ID)
    //-------------------------------------------------------------------------------------------------------
    override suspend fun InsertarCargaRL(item: CargaAcademicaItem) = DAO.InsertarCARGA(item)
    override suspend fun ObtenerCargaRL(ID: Int) = DAO.ObteneCarga(ID)
    //-------------------------------------------------------------------------------------------------------
    override suspend fun InsertarCalificacionesRL(item: Calificaciones)= DAO.InsertarCalificaciones(item)

    override suspend fun ObtenerCalificacionesRL(ID: Int) = DAO.ObteneCalificaciones(1)
}