package com.herkiewaxmann

import android.app.Application
import com.herkiewaxmann.userdirectory.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class UserDirectoryApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@UserDirectoryApp)
            modules(appModule)
        }
    }
}
