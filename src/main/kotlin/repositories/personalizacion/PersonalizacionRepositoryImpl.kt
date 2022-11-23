package repositories.personalizacion

import entities.*
import kotlinx.coroutines.Dispatchers
import mappers.fromMaquinaDaoToMaquina
import mappers.fromPersonalizacionDaoToPersonalizacion
import models.Maquina
import models.Personalizacion
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PersonalizacionRepositoryImpl(
    private val personalizacionDao: UUIDEntityClass<PersonalizacionDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>
): IPersonalizacionRepository {
    override suspend fun readAll(): List<Personalizacion> = newSuspendedTransaction(Dispatchers.IO) {
        personalizacionDao.all().map { it.fromPersonalizacionDaoToPersonalizacion(
            tareaDao, productoDao, userDao
        )}
    }

    override suspend fun findById(id: UUID): Personalizacion? = newSuspendedTransaction(Dispatchers.IO) {
        personalizacionDao.findById(id)?.fromPersonalizacionDaoToPersonalizacion(
            tareaDao, productoDao, userDao
        )
    }

    override suspend fun create(entity: Personalizacion): Personalizacion = newSuspendedTransaction(Dispatchers.IO) {
        val exists = personalizacionDao.findById(entity.id)
        exists?.let { update(entity, exists) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Personalizacion, exists: PersonalizacionDao): Personalizacion {
        return exists.apply {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(entity.raqueta, entity.user)
    }

    fun insert(entity: Personalizacion): Personalizacion {
        return personalizacionDao.new(entity.id) {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(entity.raqueta, entity.user)
    }

    override suspend fun delete(entity: Personalizacion): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val existe = personalizacionDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}