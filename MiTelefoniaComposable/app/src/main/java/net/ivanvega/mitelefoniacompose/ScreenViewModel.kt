package net.ivanvega.mitelefoniacompose

import android.app.Application
import android.telephony.SmsManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class ScreenViewModel: ViewModel() {

    fun sendSMS(a : String){
        val smsManage = SmsManager.getDefault()
        smsManage.sendTextMessage("4451129186",
            null,
            "RECIVIO UNA LLAMADA DE + ${a}",null,null
            )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ScreenViewModel()
            }
        }
    }

}