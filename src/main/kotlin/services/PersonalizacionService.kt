package services

import dto.PersonalizacionDTO
import entities.PersonalizacionDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import mappers.TareaMapper
import models.Personalizacion
import models.Tarea
import models.enums.TipoMaquina
import models.enums.TipoTarea
import repositories.personalizacion.PersonalizacionRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import java.util.UUID

class PersonalizacionService: BaseService<Personalizacion, UUID, PersonalizacionRepositoryImpl>(
    PersonalizacionRepositoryImpl(
    PersonalizacionDao, TareaDao, ProductoDao, UserDao
)) {
    val tareaRepo = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    val mapper = TareaMapper()

    fun getAllPersonalizaciones(): List<PersonalizacionDTO> {
        return mapper.toPersonalizacionDTO(this.findAll())
    }

    fun getPersonalizacionById(id: UUID): PersonalizacionDTO? {
        return this.findById(id)?.let { mapper.toPersonalizacionDTO(it) }
    }

    fun createPersonalizacion(personalizacion: PersonalizacionDTO): PersonalizacionDTO {
        val tarea = Tarea(
            id = personalizacion.id,
            raqueta = personalizacion.raqueta,
            precio = personalizacion.precio,
            user = personalizacion.user,
            tipoTarea = TipoTarea.PERSONALIZACION
        )
        tareaRepo.create(tarea)
        return mapper.toPersonalizacionDTO(this.insert(mapper.fromPersonalizacionDTO(personalizacion)))
    }

    fun deletePersonalizacion(personalizacion: PersonalizacionDTO): Boolean {
        return this.delete(mapper.fromPersonalizacionDTO(personalizacion))
    }
}