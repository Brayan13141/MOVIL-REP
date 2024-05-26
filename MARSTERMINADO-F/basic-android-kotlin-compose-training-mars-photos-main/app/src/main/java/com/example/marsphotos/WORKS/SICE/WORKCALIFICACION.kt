package com.example.marsphotos.WORKS.SICE



import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.MarsPhotosApplication

private const val TAG = "WORKERCALIFICACIONES"

class WorkerCalificaciones(ctx: Context, params: WorkerParameters, ) : CoroutineWorker(ctx, params) {

    var repoSice = (ctx.applicationContext as MarsPhotosApplication).container.REP
    override suspend fun doWork(): Result{//

        return try {
            val CALIFICACIONES = repoSice.Calificaciones() // Llamar a la función Login de REPOSICE

            Log.d("WORKER CARGA", CALIFICACIONES.toString())
            if (CALIFICACIONES != null) {
                // Inicio de sesión correcto: realizar las tareas en segundo plano necesarias según la lógica de su aplicación
                Log.d(TAG, " CALIFICACIONES RECUPERADAS")

                var datos = workDataOf(
                    "CALIFICACIONES" to CALIFICACIONES.toString()
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
