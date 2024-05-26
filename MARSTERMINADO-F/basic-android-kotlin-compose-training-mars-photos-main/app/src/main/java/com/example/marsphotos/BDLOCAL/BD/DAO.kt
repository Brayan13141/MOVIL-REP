package com.example.marsphotos.BDLOCAL.BD

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertarDetalles(item: EntityDetalles)
    @Query("SELECT * from Detalles WHERE id = :id")
    fun ObtenerDetalles(id: Int): EntityDetalles
//----------------------------FECHA------------------------
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun InsertarFecha(item: FECHA)

@Query("SELECT * from FECHA WHERE id = :id")
fun ObtenerFecha(id: Int): FECHA

//------------------------CARGA---------------------
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun InsertarCARGA(item: CargaAcademicaItem)

@Query("SELECT * from CARGA WHERE idCarga = :id")
fun ObteneCarga(id: Int): Flow<List<CargaAcademicaItem>>
//----------------------------------------------------------
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun InsertarCalificaciones(item: Calificaciones)

@Query("SELECT * from calificaciones WHERE idCali = :id")
fun ObteneCalificaciones(id: Int): Flow<List<Calificaciones>>




}