package ir.apptune.antispam.repository

import ir.apptune.antispam.repository.local.LocalRepository
import ir.apptune.antispam.room.RoomDAO

class Repository(private val local: LocalRepository) : RoomDAO {

    override suspend fun getAddress(number: String): String? {
        var userNumber = number
        if (userNumber.substring(0, 1)[0] == '*')
            return "کد دستوری"

        if (userNumber.substring(0, 1)[0] == '0')
            userNumber = "+98" + userNumber.substring(1)

        userNumber = userNumber.replace("+98", "")
        var maxLength = 8
        if (userNumber.length < maxLength) maxLength = userNumber.length
        for (i in maxLength downTo 2) {
            val address = local.getAddress(userNumber.substring(0, i))
            if (address != null) return address
        }
        return null
    }

}