package ir.apptune.antispam.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RoomDAO {

    @Query("SELECT area FROM phone_location WHERE _id = :number")
    suspend fun getAddress(number: String): String?
}