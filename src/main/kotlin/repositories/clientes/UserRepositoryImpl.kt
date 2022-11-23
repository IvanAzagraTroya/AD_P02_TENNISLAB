package repositories.clientes

import entities.UserDao
import entities.UserTable
import kotlinx.coroutines.Dispatchers
import mappers.fromUserDaoToUser
import models.User
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepositoryImpl(
    private val clientesDao: UUIDEntityClass<UserDao>,
): IUserRepository {
    override suspend fun readAll(): List<User> = newSuspendedTransaction(Dispatchers.IO) {
        clientesDao.all().map { it.fromUserDaoToUser() }
    }

    override suspend fun findById(id: UUID): User? = newSuspendedTransaction(Dispatchers.IO) {
        clientesDao.findById(id)?.fromUserDaoToUser()
    }

    override suspend fun findByEmail(email: String): User? = newSuspendedTransaction(Dispatchers.IO) {
        clientesDao.find { UserTable.email eq email }.firstOrNull()?.fromUserDaoToUser()
    }

    override suspend fun findByPhone(phone: String): User? = newSuspendedTransaction(Dispatchers.IO) {
        clientesDao.find { UserTable.email eq phone }.firstOrNull()?.fromUserDaoToUser()
    }

    override suspend fun create(entity: User): User = newSuspendedTransaction(Dispatchers.IO) {
        val existe = clientesDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: kotlin.run {
            insert(entity)
        }
    }

    fun insert(entity: User): User {
        return clientesDao.new(entity.id) {
            nombre = entity.nombre
            apellido = entity.apellido
            telefono = entity.telefono
            email = entity.email
            password = entity.password
            perfil = entity.perfil
        }.fromUserDaoToUser()
    }

    private fun update(entity: User, existe: UserDao): User {
        return existe.apply {
            nombre = entity.nombre
            apellido = entity.apellido
            telefono = entity.telefono
            email = entity.email
            password = entity.password
        }.fromUserDaoToUser()
    }

    override suspend fun delete(entity: User): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val existe = clientesDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}