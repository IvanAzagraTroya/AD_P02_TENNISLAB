package repositories.clientes

import entities.UserDao
import entities.UserTable
import mappers.fromUserDaoToUser
import models.User
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepositoryImpl(
    private val clientesDao: UUIDEntityClass<UserDao>,
): IUserRepository {
    override fun readAll(): List<User> = transaction{
        clientesDao.all().map { it.fromUserDaoToUser() }
    }

    override fun findById(id: UUID): User? = transaction {
        clientesDao.findById(id)?.fromUserDaoToUser()
    }

    override fun findByEmail(email: String): User? = transaction {
        clientesDao.find { UserTable.email eq email }.firstOrNull()?.fromUserDaoToUser()
    }

    override fun findByPhone(phone: String): User? = transaction {
        clientesDao.find { UserTable.email eq phone }.firstOrNull()?.fromUserDaoToUser()
    }

    override fun create(entity: User): User {
        val existe = clientesDao.findById(entity.id)
        return existe?.let {
            update(entity, existe)
        } ?: kotlin.run {
            insert(entity)
        }
    }

    fun insert(entity: User): User = transaction {
        clientesDao.new(entity.id) {
            nombre = entity.nombre
            apellido = entity.apellido
            telefono = entity.telefono
            email = entity.email
            password = entity.password
        }.fromUserDaoToUser()
    }

    private fun update(entity: User, existe: UserDao): User = transaction {
        existe.apply {
            nombre = entity.nombre
            apellido = entity.apellido
            telefono = entity.telefono
            email = entity.email
            password = entity.password
        }.fromUserDaoToUser()
    }

    override fun delete(entity: User): Boolean = transaction{
        val existe = clientesDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}