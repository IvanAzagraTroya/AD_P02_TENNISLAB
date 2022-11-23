package repositories.clientes

import models.User
import repositories.ICRUDRepository
import java.util.UUID

interface IUserRepository: ICRUDRepository<User, UUID> {
    suspend fun findByEmail(email: String): User?
    suspend fun findByPhone(phone: String): User?
}