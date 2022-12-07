package repositories.clientes

import kotlinx.coroutines.Deferred
import models.User
import repositories.ICRUDRepository
import java.util.UUID

interface IUserRepository: ICRUDRepository<User, UUID> {
    suspend fun findByEmail(email: String): Deferred<User?>
    suspend fun findByPhone(phone: String): Deferred<User?>
}