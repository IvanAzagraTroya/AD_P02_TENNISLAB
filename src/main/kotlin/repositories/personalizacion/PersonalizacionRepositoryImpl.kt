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

    override fun create(entity: Personalizacion): Personalizacion {
        val exists = personalizacionDao.findById(entity.id)
        return exists?.let { update(entity, exists) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Personalizacion, exists: PersonalizacionDao): Personalizacion = transaction {
        exists.apply {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(entity.raqueta, entity.user)
    }

    fun insert(entity: Personalizacion): Personalizacion = transaction {
        personalizacionDao.new(entity.id) {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(entity.raqueta, entity.user)
    }

    override fun delete(entity: Personalizacion): Boolean = transaction {
        val existe = personalizacionDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}