package ir.apptune.antispam.base.di

import androidx.room.Room
import ir.apptune.antispam.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {

    single {
        val db = Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "locations.db")
                .createFromAsset("locations.db")
                .build()
        db.userDao()
    }
}