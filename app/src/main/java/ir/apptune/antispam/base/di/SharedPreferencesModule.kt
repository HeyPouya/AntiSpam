package ir.apptune.antispam.base.di

import android.app.Activity
import ir.apptune.antispam.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single {
        androidContext().getSharedPreferences(androidContext().getString(R.string.app_name), Activity.MODE_PRIVATE)
    }
}