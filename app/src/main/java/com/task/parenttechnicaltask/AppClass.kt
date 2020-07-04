package com.task.parenttechnicaltask

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.tests.newandroid.DI.appModule
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class AppClass : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        val koin: KoinApplication = KoinAndroidApplication.create(this)
            .modules(appModule)
        startKoin(GlobalContext(), koin)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        var instance: AppClass? = null
            private set
    }
}