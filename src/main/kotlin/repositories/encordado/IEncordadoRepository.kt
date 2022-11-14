package repositories.encordado

import models.Encordado
import repositories.ICRUDRepository
import java.util.UUID

interface IEncordadoRepository: ICRUDRepository<Encordado, UUID> {
}