package repositories.personalizacion

import models.Personalizacion
import repositories.ICRUDRepository
import java.util.UUID

interface IPersonalizacionRepository: ICRUDRepository<Personalizacion, UUID> {
}