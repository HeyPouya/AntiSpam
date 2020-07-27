package ir.apptune.antispam.base.di

import ir.apptune.antispam.features.callog.CallDetailsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CallDetailsViewModel(androidApplication(),get()) }
}