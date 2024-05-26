package com.example.regreso_casa.INTERFAZ

import android.telecom.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("/v2/directions/driving-car")
    suspend fun getRoute(@Query("api_key")key:String, @Query("start",
        encoded = true)start:String, @Query("end",
        encoded = true)end:String)
            : Response<com.example.regreso_casa.INTERFAZ.Response>
}