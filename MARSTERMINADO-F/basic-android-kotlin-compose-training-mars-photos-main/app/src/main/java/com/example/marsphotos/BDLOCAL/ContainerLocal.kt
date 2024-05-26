package com.example.marsphotos.BDLOCAL

import android.content.Context
import androidx.work.WorkerParameters
import com.example.marsphotos.BDLOCAL.BD.BD
import com.example.marsphotos.BDLOCAL.REPO.REPOIMPLEMENT
import com.example.marsphotos.BDLOCAL.REPO.REPOSUSPEND
import com.example.marsphotos.data.Iniciar
import com.example.marsphotos.data.REPOSICE


interface AppContainer {
    val REPOLOCAL: REPOSUSPEND
}
class ContainerLocal(private val context: Context) : AppContainer {

    override val REPOLOCAL: REPOSUSPEND  by lazy {
        REPOIMPLEMENT(BD.getDatabase(context).DAO())
    }

}