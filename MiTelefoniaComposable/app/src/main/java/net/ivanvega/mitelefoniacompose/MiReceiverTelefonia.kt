import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class MiReceiverTelefonia : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
try {
    if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_RINGING) {

            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            Toast.makeText(context,"RECIBIO EL BROADCAST"+ phoneNumber,Toast.LENGTH_SHORT).show()
            enviarMensaje(context, phoneNumber)
        }
    }
}catch (E:Exception)
{
    Toast.makeText(context,"ERROR AL RECIBIR EL BROADCAST",Toast.LENGTH_SHORT).show()

}

    }
    private fun enviarMensaje(context: Context, phoneNumber: String?) {
        val smsManager = SmsManager.getDefault()
        val message = "RECIBISTE UNA LLAMADA DE EL NUMERO: $phoneNumber"
        val recipientNumber = "4451129186"

        try {
            smsManager.sendTextMessage(recipientNumber, null, message, null, null)
            Toast.makeText(context, "MENSAJE ENVIADO A: $recipientNumber", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show()
            Log.e("MiReceiverTelefonia", "Error al enviar el mensaje", e)
            e.printStackTrace()
        }
    }
}
