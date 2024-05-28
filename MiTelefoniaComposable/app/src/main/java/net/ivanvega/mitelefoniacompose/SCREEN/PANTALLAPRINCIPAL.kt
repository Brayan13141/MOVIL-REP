package net.ivanvega.mitelefoniacompose.SCREEN

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.channels.Channel
import net.ivanvega.mitelefoniacompose.ScreenViewModel
import net.ivanvega.mitelefoniacompose.ScreenViewModel.Companion.Factory

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PantallaPrincipal(
  VIEW : ScreenViewModel = viewModel()

)
{
    val Permiso = rememberPermissionState(permission = android.Manifest.permission.SEND_SMS)
  Column {
      Button(onClick = {
              Permiso.launchPermissionRequest()
          if (Permiso.status.isGranted)
          {
              VIEW.sendSMS("JNBHUYV")
          }
      }) {

      }
  }
}