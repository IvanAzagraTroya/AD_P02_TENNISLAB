package repositories.user

import models.User
import repositories.ICRUDRepository
import java.util.UUID

interface IUserRepository: ICRUDRepository<User, UUID> {
}