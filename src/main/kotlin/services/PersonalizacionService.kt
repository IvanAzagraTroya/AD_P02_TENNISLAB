package services

import dto.PersonalizacionDTO
import dto.TareaDTO
import entities.*
import kotlinx.coroutines.flow.toList
import mappers.TareaMapper
import models.Personalizacion
import models.Tarea
import models.enums.TipoTarea
import repositories.personalizacion.PersonalizacionRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import java.util.UUID

class PersonalizacionService: BaseService<Personalizacion, UUID, PersonalizacionRepositoryImpl>(
    PersonalizacionRepositoryImpl(
    PersonalizacionDao, TareaDao
)) {
    val tareaRepo = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    val mapper = TareaMapper()

    suspend fun getAllPersonalizaciones(): List<PersonalizacionDTO> {
        return mapper.toPersonalizacionDTO(this.findAll().toList())
    }

    suspend fun getPersonalizacionById(id: UUID): PersonalizacionDTO? {
        return this.findById(id).await()?.let { mapper.toPersonalizacionDTO(it) }
    }

    suspend fun createPersonalizacion(personalizacion: PersonalizacionDTO): TareaDTO {
        val tarea = Tarea(
            id = personalizacion.id,
            raqueta = personalizacion.raqueta,
            precio = personalizacion.precio,
            user = personalizacion.user,
            tipoTarea = TipoTarea.PERSONALIZACION
        )
        tareaRepo.create(tarea).await()
        return mapper.toDTO(this.insert(mapper.fromPersonalizacionDTO(personalizacion)).await())
    }

    suspend fun deletePersonalizacion(personalizacion: PersonalizacionDTO): Boolean {
        return this.delete(mapper.fromPersonalizacionDTO(personalizacion)).await()
    }
}