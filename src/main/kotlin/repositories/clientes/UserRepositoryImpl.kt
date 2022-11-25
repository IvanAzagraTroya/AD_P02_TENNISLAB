package repositories.clientes

import entities.UserDao
import entities.UserTable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromUserDaoToUser
import models.User
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepositoryImpl(
    private val clientesDao: UUIDEntityClass<UserDao>,
): IUserRepository {
    override suspend fun readAll(): Flow<User> = newSuspendedTransaction(Dispatchers.IO) {
        clientesDao.all().map { it.fromUserDaoToUser() }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<User?> = suspendedTransactionAsync(Dispatchers.IO) {
        clientesDao.findById(id)?.fromUserDaoToUser()
    }

    override suspend fun findByEmail(email: String): Deferred<User?> = suspendedTransactionAsync(Dispatchers.IO) {
        clientesDao.find { UserTable.email eq email }.firstOrNull()?.fromUserDaoToUser()
    }

    override suspend fun findByPhone(phone: String): Deferred<User?> = suspendedTransactionAsync(Dispatchers.IO) {
        clientesDao.find { UserTable.telefono eq phone }.firstOrNull()?.fromUserDaoToUser()
    }

    override suspend fun create(entity: User): Deferred<User> = suspendedTransactionAsync(Dispatchers.IO) {
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

    override suspend fun delete(entity: User): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = clientesDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}