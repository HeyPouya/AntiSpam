package ir.apptune.antispam.repository.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RoomDAO {

    @Query("SELECT * FROM locations WHERE number = _id")
    suspend fun getAddress(number:String):String?
}