package ir.apptune.antispam.base

import android.app.Application
import ir.apptune.antispam.base.di.repositoryModule
import ir.apptune.antispam.base.di.roomModule
import ir.apptune.antispam.base.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(roomModule, repositoryModule, viewModelModule)
        }
    }
}