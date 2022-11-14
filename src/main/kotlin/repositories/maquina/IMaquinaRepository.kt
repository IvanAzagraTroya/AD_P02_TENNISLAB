package repositories.maquina

import models.Maquina
import repositories.ICRUDRepository
import java.util.UUID

interface IMaquinaRepository: ICRUDRepository<Maquina, UUID> {
}