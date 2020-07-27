package ir.apptune.antispam.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RoomDAO {

    @Query("SELECT * FROM locations WHERE number = _id")
    suspend fun getAddress(number:String):String?
}