package ir.apptune.antispam.base

import android.app.Application
import ir.apptune.antispam.base.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * BaseApplication class to initialize Koin
 *
 */
class BaseApplication : Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(roomModule, repositoryModule, viewModelModule, utilsModule, sharedPreferencesModule)
        }
    }
}