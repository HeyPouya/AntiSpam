package ir.apptune.antispam.base.di

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ir.apptune.antispam.utils.CallDetailClass
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {

    single {
        CallDetailClass(androidContext(), get(), ContextCompat.checkSelfPermission(androidContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
    }
}