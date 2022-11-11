package repositories.clientes

import models.User
//import models.UsersDao
import repositories.ICRUDRepository
import java.util.UUID

interface IClientesRepository: ICRUDRepository<User, UUID> {
    fun findAll(): List<User>
    fun findById(id: UUID): User?
}