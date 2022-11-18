package repositories.personalizacion

import entities.*
import mappers.fromMaquinaDaoToMaquina
import mappers.fromPersonalizacionDaoToPersonalizacion
import models.Maquina
import models.Personalizacion
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PersonalizacionRepositoryImpl(
    private val personalizacionDao: UUIDEntityClass<PersonalizacionDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>
): IPersonalizacionRepository {
    override fun readAll(): List<Personalizacion> = transaction {
        personalizacionDao.all().map { it.fromPersonalizacionDaoToPersonalizacion(
            tareaDao, productoDao, userDao
        )}
    }

    override fun findById(id: UUID): Personalizacion? = transaction {
        personalizacionDao.findById(id)?.fromPersonalizacionDaoToPersonalizacion(
            tareaDao, productoDao, userDao
        )
    }

    override fun create(entity: Personalizacion): Personalizacion = transaction {
        val exists = personalizacionDao.findById(entity.id)
        exists?.let { update(entity, exists) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Personalizacion, exists: PersonalizacionDao): Personalizacion {
        return exists.apply {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(tareaDao, productoDao, userDao)
    }

    private fun insert(entity: Personalizacion): Personalizacion {
        return personalizacionDao.new(entity.id) {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(tareaDao, productoDao, userDao)
    }

    override fun delete(entity: Personalizacion): Boolean = transaction {
        val existe = personalizacionDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}