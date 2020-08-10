package ir.apptune.antispam.repository.local

import ir.apptune.antispam.room.RoomDAO

/**
 * Local repository
 *
 * @property dao
 */
class LocalRepository(private val dao: RoomDAO) : RoomDAO {

    override suspend fun getAddress(number: String) = dao.getAddress(number)
}