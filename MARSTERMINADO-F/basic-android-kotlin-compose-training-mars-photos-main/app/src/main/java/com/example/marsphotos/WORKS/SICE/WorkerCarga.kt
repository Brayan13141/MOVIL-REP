package com.example.marsphotos.WORKS.SICE


import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.MarsPhotosApplication

private const val TAG = "WORKERCALIFICACIONES_workerCargar"

class WorkerCarga(ctx: Context, params: WorkerParameters,

) : CoroutineWorker(ctx, params) {
    val para = params.inputData

    var repoSice = (ctx.applicationContext as MarsPhotosApplication).container.REP
    override suspend fun doWork(): Result{
        return try {
            val CARGA = repoSice.CargaAcademicaByAlumno() // Llamar a la función Login de REPOSICE

            Log.d("WORKER CARGA", CARGA)
            if (CARGA != null) {
                // Inicio de sesión correcto: realizar las tareas en segundo plano necesarias según la lógica de su aplicación
                Log.d(TAG, "CARGA ACADEMICA RECUPERADA")

                var datos = workDataOf(
                    "CARGA" to CARGA.toString()
                )
                Result.success(datos)

            } else {
                // Error de inicio de sesión: manejar el error de forma adecuada
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error durante el inicio de sesión: ${e.message}", e)
            Result.failure()
        }


    }
}
