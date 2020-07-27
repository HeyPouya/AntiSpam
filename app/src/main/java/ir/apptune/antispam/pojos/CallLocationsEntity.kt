package ir.apptune.antispam.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone_location")
data class CallLocationsEntity(
        @PrimaryKey
        val _id: Int,
        val area: String
)