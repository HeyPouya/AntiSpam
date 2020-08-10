package ir.apptune.antispam.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Dataclass to use database via Room
 *
 * @property _id phone number's zone
 * @property area address
 */
@Entity(tableName = "phone_location")
data class CallLocationsEntity(
        @PrimaryKey
        val _id: Int,
        val area: String
)