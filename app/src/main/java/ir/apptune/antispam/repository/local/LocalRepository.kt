package ir.apptune.antispam.repository.local

class LocalRepository(private val dao: RoomDAO) : RoomDAO {

    override suspend fun getAddress(number: String) = dao.getAddress(number)
}