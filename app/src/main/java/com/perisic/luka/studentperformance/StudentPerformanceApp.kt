package com.perisic.luka.studentperformance

import android.app.Application
import com.perisic.luka.data.di.repositoryModule
import com.perisic.luka.data.local.di.localModule
import com.perisic.luka.data.remote.di.apiModule
import com.perisic.luka.data.remote.di.remoteModule
import com.perisic.luka.studentperformance.di.VMModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StudentPerformanceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StudentPerformanceApp)
            modules(
                listOf(
                    remoteModule,
                    repositoryModule,
                    apiModule,
                    localModule,
                    VMModule
                )
            )
        }
    }

}