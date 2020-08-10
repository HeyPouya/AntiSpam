package ir.apptune.antispam.repository

import ir.apptune.antispam.repository.local.LocalRepository
import ir.apptune.antispam.room.RoomDAO

/**
 * All repositories
 *
 * @property local
 */
class Repository(private val local: LocalRepository) : RoomDAO {

    override suspend fun getAddress(number: String): String? {
        var userNumber = number
        var maxLength = 8

        if (!(userNumber.startsWith('*') || userNumber.startsWith('0') || userNumber.startsWith("0098") || userNumber.startsWith("+98") || userNumber.startsWith("98")))
            return "شماره وارد شده اشتباه است و یا در بانک اطلاعاتی موجود نیست"

        when {
            userNumber.startsWith('*') -> return "کد دستوری"
            userNumber.startsWith("0098") -> userNumber = userNumber.replaceFirst("0098", "")
            userNumber.startsWith("+98") -> userNumber = userNumber.replaceFirst("+98", "")
            userNumber.startsWith("98") -> userNumber = userNumber.replaceFirst("98", "")
            userNumber.startsWith('0') -> userNumber = userNumber.replaceFirst("0", "")
        }

        if (userNumber.length < maxLength) maxLength = userNumber.length
        for (i in maxLength downTo 2) {
            val address = local.getAddress(userNumber.substring(0, i))
            if (address != null) return address
        }
        return null
    }

}