package com.example.futbolitopocket


import ElementosCampo
import MyScreen
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.compose.runtime.mutableStateOf


import android.hardware.SensorEvent


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.futbolitopocket.utilities.checkCollision

class MainActivity : ComponentActivity(),SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // Score counters for each goal
    private var blueScore by mutableStateOf(0)
    private var redScore by mutableStateOf(0)


    private var posX = mutableStateOf(0f)
    private var posY = mutableStateOf(0f)
    private var velX = mutableStateOf(0f)
    private var velY = mutableStateOf(0f)
    var RADIUS= 10.dp
    // Sensitivity factor
    private val sensitivityFactor = 4f
    private val zFactor = 0.5f // Factor de ajuste para el eje Z

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtiene una instancia del SensorManager para interactuar con los sensores del dispositivo
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

       // Obtiene una referencia al sensor de acelerómetro predeterminado del dispositivo
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        // para escuchar eventos del sensor de acelerómetro.
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)


        setContent {
            // Obtiene la configuración actual del dispositivo
            val configuration = LocalConfiguration.current
           // Obtiene la densidad actual del dispositivo
            val density = LocalDensity.current

         // Define el radio del círculo en dp
            val circleRadiusDp = RADIUS
          // Convierte las medidas de la pantalla y el radio del circulo de dp a px
            val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
            val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
            val circleRadiusPx = with(density) { circleRadiusDp.toPx() }

         // Efecto lanzado una vez cuando el componente es lanzado
            LaunchedEffect(Unit) {
                // Inicializa la posición al centro de la pantalla
                posX.value = (screenWidthPx / 2)
                posY.value = (screenHeightPx / 2)
            }

            // Define las posiciones de los cuadrados
            val squareSizeDp = 70.dp
            val squareSizePx = with(density) { squareSizeDp.toPx() }
            val squares = remember {
                listOf(
                    Offset(screenWidthPx / 4 - squareSizePx / 2, screenHeightPx / 6 - squareSizePx / 2),
                    Offset(screenWidthPx / 2 - squareSizePx / 2, screenHeightPx / 6 - squareSizePx / 2),
                    Offset(3 * screenWidthPx / 4 - squareSizePx / 2, screenHeightPx / 6 - squareSizePx / 2),
                    Offset(screenWidthPx / 3 - squareSizePx / 2, screenHeightPx / 2 - squareSizePx / 2),
                    Offset(2 * screenWidthPx / 3 - squareSizePx / 2, screenHeightPx / 2 - squareSizePx / 2),
                    Offset(screenWidthPx / 4 - squareSizePx / 2, 5 * screenHeightPx / 6 - squareSizePx / 2),
                    Offset(screenWidthPx / 2 - squareSizePx / 2, 5 * screenHeightPx / 6 - squareSizePx / 2),
                    Offset(3 * screenWidthPx / 4 - squareSizePx / 2, 5 * screenHeightPx / 6 - squareSizePx / 2)
                )
            }

           MyScreen {
               ElementosCampo(
                   x =posX.value ,
                   y = posY.value,
                   circleRadiusPx =circleRadiusPx ,
                   screenWidthPx = screenWidthPx,
                   screenHeightPx = screenHeightPx,
                   squares =squares ,
                   squareSizePx = squareSizeDp.value,
                   blueScore = blueScore,
                   redScore = redScore
               ) {

               }
           }

           
        }

    }
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // Actualiza la velocidad basada en los valores del acelerómetro y el factor de sensibilidad
       //SE DEFINE LA DIRECCION DE LA PELOTA SUMANDO Y RESTANDO LOS FACTORES
            velX.value -= it.values[0] * sensitivityFactor
            velY.value += it.values[1] * sensitivityFactor
            val velZ = it.values[2] * zFactor
            // Incorporar velZ en la lógica de movimiento
            velX.value += velZ
            velY.value += velZ

            // Update position based on velocity
            posX.value += velX.value
            posY.value += velY.value

            val maxX = resources.displayMetrics.widthPixels.toFloat()
            val maxY = resources.displayMetrics.heightPixels.toFloat()

            val density = resources.displayMetrics.density
// Convertir el radio del círculo de dp a px usando la densidad de la pantalla
            val circleRadiusPx = density * 10 // Ejemplo con un radio de 10dp

// Rebote en los bordes
            if (posX.value - circleRadiusPx < 0f || posX.value + circleRadiusPx > maxX) {
                // Si la posición X de la pelota menos su radio es menor a 0 (colisión con el borde izquierdo)
                // O si la posición X de la pelota más su radio es mayor a maxX (colisión con el borde derecho)
                velX.value = -velX.value * 0.299f // Invertir la velocidad X y aplicar amortiguación para simular pérdida de energía
                posX.value = posX.value.coerceIn(circleRadiusPx, maxX - circleRadiusPx) // Asegurar que la pelota se mantenga dentro de los límites
            }
            if (posY.value - circleRadiusPx < 0f || posY.value + circleRadiusPx > maxY) {
                // Si la posición Y de la pelota menos su radio es menor a 0 (colisión con el borde superior)
                // O si la posición Y de la pelota más su radio es mayor a maxY (colisión con el borde inferior)
                velY.value = -velY.value * 0.299f // Invertir la velocidad Y y aplicar amortiguación para simular pérdida de energía
                posY.value = posY.value.coerceIn(circleRadiusPx, maxY - circleRadiusPx) // Asegurar que la pelota se mantenga dentro de los límites
            }

// Detección de goles
            val goalTop = 0f // La coordenada Y del borde superior de la portería superior
            val goalBottom = maxY / 26 // La altura de la portería superior
            val goalWidth = maxX / 8 // El ancho de ambas porterías

            val ballCenterX = posX.value // La posición X del centro de la pelota
            val ballCenterY = posY.value // La posición Y del centro de la pelota

// Verificar si la pelota está dentro de la portería superior
            val isInsideTopGoal = ballCenterY - circleRadiusPx > goalTop && ballCenterY - circleRadiusPx < goalBottom &&
                    ballCenterX > (maxX - goalWidth) / 2 && ballCenterX < (maxX + goalWidth) / 2

// Verificar si la pelota está dentro de la portería inferior
            val isInsideBottomGoal = ballCenterY + circleRadiusPx > maxY - goalBottom && ballCenterY + circleRadiusPx < maxY &&
                    ballCenterX > (maxX - goalWidth) / 2 && ballCenterX < (maxX + goalWidth) / 2

            if (isInsideTopGoal || isInsideBottomGoal) {
                // Si la pelota está dentro de cualquiera de las porterías
                val goalColor = if (isInsideTopGoal) Color.Blue else Color.Gray // Determinar el color de la portería (Azul o Gris)

                posX.value = maxX / 2 // Reiniciar la posición X de la pelota al centro de la pantalla
                posY.value = maxY / 2 // Reiniciar la posición Y de la pelota al centro de la pantalla
                velX.value = 0f // Detener la velocidad X
                velY.value = 0f // Detener la velocidad Y

                onGoalScored(goalColor) // Llamar a la función onGoalScored pasando el color de la portería donde se anotó el gol
            }

            // Check for collisions with squares
            val squares = listOf(
                Offset(maxX / 4 - circleRadiusPx / 2, maxY / 6 - circleRadiusPx / 2),
                Offset(maxX / 2 - circleRadiusPx / 2, maxY / 6 - circleRadiusPx / 2),
                Offset(3 * maxX / 4 - circleRadiusPx / 2, maxY / 6 - circleRadiusPx / 2),
                Offset(maxX / 3 - circleRadiusPx / 2, maxY / 2 - circleRadiusPx / 2),
                Offset(2 * maxX / 3 - circleRadiusPx / 2, maxY / 2 - circleRadiusPx / 2),
                Offset(maxX / 4 - circleRadiusPx / 2, 5 * maxY / 6 - circleRadiusPx / 2),
                Offset(maxX / 2 - circleRadiusPx / 2, 5 * maxY / 6 - circleRadiusPx / 2),
                Offset(3 * maxX / 4 - circleRadiusPx / 2, 5 * maxY / 6 - circleRadiusPx / 2)
            )
            for (square in squares) {
                if (checkCollision(posX.value, posY.value, circleRadiusPx, square, circleRadiusPx)) {
                    velX.value = -velX.value
                    velY.value = -velY.value
                }
            }
        }

    }


    private fun onGoalScored(color: Color){

        // Esta función se llama cada vez que se anota un gol
        if (color == Color.Blue) {
            // Aumentar el marcador del equipo azul
            redScore++

        } else if (color == Color.Gray) {
            // Aumentar el marcador del equipo gris
            blueScore++
        }


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}


}




