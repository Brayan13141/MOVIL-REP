package com.example.marsphotos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class DETALLESALUMNO(
    var matricula : String ="",
    var contrasenia : String="",
    var estatus : String="",
    var acceso : String=""
)


data class CargaAcademicaML(
      val id : Int?,
    val Semipresencial: String = "",
    val Observaciones: String = "",
    val Docente: String = "",
    val clvOficial: String = "",
    val Sabado: String = "",
    val Viernes: String = "",
    val Jueves: String = "",
    val Miercoles: String = "",
    val Martes: String = "",
    val Lunes: String = "",
    val EstadoMateria: String = "",
    val CreditosMateria: Int = 0,
    val Materia: String = "",
    val Grupo: String = ""
)

data class CalificacionesML(
    val id : Int?,
    val Observaciones: String = "",
    val C13: String = "", // Puedes cambiar el tipo de dato según lo que debería ser
    val C12: String = "",
    val C11: String = "",
    val C10: String = "",
    val C9: String = "",
    val C8: String = "",
    val C7: String = "",
    val C6: String = "",
    val C5: String = "",
    val C4: String = "",
    val C3: String = "",
    val C2: String = "",
    val C1: String = "",
    val UnidadesActivas: String= "",
    val Materia: String= "",
    val Grupo: String= ""
)

