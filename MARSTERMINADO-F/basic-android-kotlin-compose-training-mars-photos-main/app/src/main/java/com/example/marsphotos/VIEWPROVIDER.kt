package com.example.marsphotos

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.data.ViewModelLocal

object VIEWPROVIDER {
    val Factory = viewModelFactory {
        initializer {
            ViewModelLocal(MarsPhotosApplication().container2.REPOLOCAL,this.MarsPhotosApplication().applicationContext)
        }
    }
}
fun CreationExtras.MarsPhotosApplication():MarsPhotosApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MarsPhotosApplication)