package com.example.marsphotos.BDLOCAL.BD

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Detalles")
data class EntityDetalles(
    @PrimaryKey
    val id: Int?,
    var Ncontrol : String = "",
    var Contraseña : String? = null,
    var Estado : String = "",
    var Acceso : String = ""
)


@Entity(tableName = "CARGA")
data class CargaAcademicaItem(
    @PrimaryKey
    val id : Int?,
    val idCarga : Int?,
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

@Entity(tableName = "calificaciones")
data class Calificaciones(
    @PrimaryKey
    val id : Int?,
    val idCali : Int?,
    val Observaciones: String = " ",
    val C13: String ?= "", // Puedes cambiar el tipo de dato según lo que debería ser
    val C12: String ?= "",
    val C11: String ?= "",
    val C10: String ?= "",
    val C9: String ?= "",
    val C8: String ?= "",
    val C7: String ?= "",
    val C6: String ?= "",
    val C5: String ?= "",
    val C4: String ?= "",
    val C3: String ?= "",
    val C2: String ?= "",
    val C1: String ?= "",
    val UnidadesActivas: String= "",
    val Materia: String= "",
    val Grupo: String= ""
)

@Entity(tableName = "Cardex")
data class Cardex(
    @PrimaryKey
    val id : Int?,
    val Grupo: String= ""
)

@Entity(tableName = "FECHA")
data class FECHA(
    @PrimaryKey
    val id : Int?,
    val Fecha: String? = "",
)
