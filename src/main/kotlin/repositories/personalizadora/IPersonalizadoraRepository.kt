package repositories.personalizadora

import models.Personalizadora
import repositories.ICRUDRepository
import java.util.UUID

interface IPersonalizadoraRepository: ICRUDRepository<Personalizadora, UUID> {
}