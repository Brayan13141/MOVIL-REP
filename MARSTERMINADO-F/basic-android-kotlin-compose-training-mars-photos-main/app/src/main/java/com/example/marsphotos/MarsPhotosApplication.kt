/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos

import android.app.Application
import androidx.work.WorkerParameters
import com.example.marsphotos.BDLOCAL.AppContainer
import com.example.marsphotos.BDLOCAL.ContainerLocal 
import com.example.marsphotos.data.Container
import com.example.marsphotos.data.DefaultContainer



class MarsPhotosApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
//CONTAINER DE SICE
    lateinit var container: Container

    //CONTAINER DE LA BDLOCAL
    lateinit var container2: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
        container2 = ContainerLocal(this)

    }
}
