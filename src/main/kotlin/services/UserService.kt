package services

import dto.UserDTO
import entities.UserDao
import mappers.UserMapper
import models.User
import repositories.clientes.UserRepositoryImpl
import java.util.UUID

class UserService: BaseService<User, UUID, UserRepositoryImpl>(UserRepositoryImpl(UserDao)) {
    val mapper = UserMapper()

    fun getAllUsers(): List<UserDTO> {
        return mapper.toDTO(this.findAll())
    }

    fun getUserById(id: UUID): UserDTO? {
        return this.findById(id)?.let { mapper.toDTO(it) }
    }

    fun getUserByMail(mail: String): UserDTO {
        return mapper.toDTO(repository.findByEmail(mail))
    }

    fun getUserByPhone(phone: String): UserDTO {
        return mapper.toDTO(repository.findByPhone(phone))
    }

    fun createUser(user: UserDTO): UserDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(user)))
    }

    fun deleteUser(user: UserDTO): Boolean {
        return this.delete(mapper.fromDTO(user))
    }
}