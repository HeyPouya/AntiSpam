package ir.apptune.antispam.base.di

import ir.apptune.antispam.features.callog.CallDetailsViewModel
import ir.apptune.antispam.features.search.SearchPhoneViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { CallDetailsViewModel(get()) }
    viewModel { SearchPhoneViewModel(get()) }
}