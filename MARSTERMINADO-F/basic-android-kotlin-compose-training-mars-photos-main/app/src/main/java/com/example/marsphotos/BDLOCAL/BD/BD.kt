package com.example.marsphotos.BDLOCAL.BD

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [EntityDetalles::class,FECHA::class,
    CargaAcademicaItem::class, Calificaciones::class,Cardex::class], version = 2, exportSchema = false)
abstract class   BD : RoomDatabase() {

    abstract fun DAO(): DAO

    companion object {
        @Volatile
        private var INSTANCE: BD? = null
        fun getDatabase(context: Context): BD {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BD::class.java,
                    "BDSICENET")
                    .fallbackToDestructiveMigration()
                    .build()
                  INSTANCE = instance
                return instance
            }
        }
    }
}