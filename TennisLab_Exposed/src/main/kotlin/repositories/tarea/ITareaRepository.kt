package repositories.tarea

import models.Tarea
import repositories.ICRUDRepository
import java.util.UUID

interface ITareaRepository: ICRUDRepository<Tarea, UUID> {
}