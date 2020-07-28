package ir.apptune.antispam.base.di

import androidx.room.Room
import ir.apptune.antispam.room.AppDatabase
import ir.apptune.antispam.utils.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {

    single {
        val db = Room.databaseBuilder(androidApplication(), AppDatabase::class.java, DATABASE_NAME)
                .createFromAsset(DATABASE_NAME)
                .build()
        db.userDao()
    }
}