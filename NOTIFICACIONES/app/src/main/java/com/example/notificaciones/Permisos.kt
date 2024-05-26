package com.example.notificaciones

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        permissionGranted = hasPermission
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (permissionGranted) {
            Text(text = "Notification Permission Granted")
        } else {
            Button(onClick = {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }) {
                Text(text = "Request Notification Permission")
            }
        }
    }
}
