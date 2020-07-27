package ir.apptune.antispam.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.apptune.antispam.pojos.CallLocationsEntity

@Database(entities = [CallLocationsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): RoomDAO
}
