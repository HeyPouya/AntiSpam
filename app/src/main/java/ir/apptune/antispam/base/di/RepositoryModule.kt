package ir.apptune.antispam.base.di

import ir.apptune.antispam.repository.Repository
import ir.apptune.antispam.repository.local.LocalRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        LocalRepository(get())
    }

    single {
        Repository(get())
    }
}