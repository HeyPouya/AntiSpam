package ir.apptune.antispam.repository

import ir.apptune.antispam.repository.local.LocalRepository
import ir.apptune.antispam.repository.local.RoomDAO

class Repository(private val local: LocalRepository) : RoomDAO {

    override suspend fun getAddress(number: String): String? {
        var maxLength = 8
        if (number.length < maxLength) maxLength = number.length
        for (i in maxLength downTo 2) {
            val address = local.getAddress(number.substring(0, i))
            if (address != null) return address
        }
        return null
    }

}