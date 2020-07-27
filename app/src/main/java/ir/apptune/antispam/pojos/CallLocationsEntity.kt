package ir.apptune.antispam.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CallLocationsEntity(
        @PrimaryKey
        val _id: Int,
        val area: String
)