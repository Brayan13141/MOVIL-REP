    package net.ivanvega.mitelefoniacompose

    import MiReceiverTelefonia
    import android.Manifest
    import android.Manifest.permission
    import android.content.BroadcastReceiver
    import android.content.Context
    import android.content.Intent
    import android.content.IntentFilter
    import android.content.pm.PackageManager
    import android.os.Build
    import android.os.Bundle
    import android.provider.Telephony
    import android.telephony.SmsMessage
    import android.telephony.TelephonyCallback
    import android.telephony.TelephonyManager
    import android.util.Log
    import android.widget.Toast
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.rememberLauncherForActivityResult
    import androidx.activity.compose.setContent
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.size
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.DisposableEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.rememberUpdatedState
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.core.content.ContextCompat
    import androidx.lifecycle.viewmodel.viewModelFactory
    import kotlinx.coroutines.channels.Channel 
    import net.ivanvega.mitelefoniacompose.ui.theme.MiTelefoniaComposeTheme

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                MiTelefoniaComposeTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        RequestPermissionAndRegisterReceiver()
                    }
                }
            }
        }
    }

    @Composable
    fun RequestPermissionAndRegisterReceiver() {
        val context = LocalContext.current
        val permissions = listOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_CALL_LOG
        )

        val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsGranted ->
            if (permissionsGranted.values.all { it }) {
                registerBroadcastReceiver(context)
            }
        }

        DisposableEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val allPermissionsGranted = permissions.all {
                    ContextCompat.checkSelfPermission(context, it) == android.content.pm.PackageManager.PERMISSION_GRANTED
                }
                if (!allPermissionsGranted) {
                    permissionLauncher.launch(permissions.toTypedArray())
                } else {
                    registerBroadcastReceiver(context)
                }
            } else {
                registerBroadcastReceiver(context)
            }
            onDispose { }
        }
    }

    private fun registerBroadcastReceiver(context: Context) {
        val intentFilter = IntentFilter().apply {
            addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        }
        val broadcastReceiver = MiReceiverTelefonia()
        context.registerReceiver(broadcastReceiver, intentFilter)
        Toast.makeText(context, "BroadcastReceiver registrado", Toast.LENGTH_SHORT).show()
    }