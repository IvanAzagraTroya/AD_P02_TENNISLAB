package services

import dto.UserDTO
import entities.UserDao
import kotlinx.coroutines.flow.toList
import mappers.UserMapper
import models.User
import repositories.clientes.UserRepositoryImpl
import java.util.UUID

class UserService: BaseService<User, UUID, UserRepositoryImpl>(UserRepositoryImpl(UserDao)) {
    val mapper = UserMapper()

    suspend fun getAllUsers(): List<UserDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getUserById(id: UUID): UserDTO? {
        return this.findById(id).await()?.let { mapper.toDTO(it) }
    }

    suspend fun getUserByMail(mail: String): UserDTO? {
        return repository.findByEmail(mail).await()?.let { mapper.toDTO(it) }
    }

    suspend fun getUserByPhone(phone: String): UserDTO? {
        return repository.findByPhone(phone).await()?.let { mapper.toDTO(it) }
    }

    suspend fun createUser(user: UserDTO): UserDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(user)).await())
    }

    suspend fun deleteUser(user: UserDTO): Boolean {
        return this.delete(mapper.fromDTO(user)).await()
    }
}