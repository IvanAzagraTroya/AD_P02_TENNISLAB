package repositories.clientes

import models.User
import repositories.ICRUDRepository
import java.util.UUID

interface IClientesRepository: ICRUDRepository<User, UUID> {
}