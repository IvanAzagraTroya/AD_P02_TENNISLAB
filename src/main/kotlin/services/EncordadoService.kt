package services

import dto.EncordadoDTO
import entities.EncordadoDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import mappers.TareaMapper
import models.Encordado
import models.Tarea
import models.enums.TipoTarea
import repositories.encordado.EncordadoRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import java.util.UUID

class EncordadoService: BaseService<Encordado, UUID, EncordadoRepositoryImpl>(
    EncordadoRepositoryImpl(
        TareaDao, ProductoDao, UserDao, EncordadoDao
)) {
    val tareaRepo = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    val mapper = TareaMapper()

    fun getAllEncordados(): List<EncordadoDTO> {
        return mapper.toEncordadoDTO(this.findAll())
    }

    fun getEncordadoById(id: UUID): EncordadoDTO? {
        return this.findById(id)?.let { mapper.toEncordadoDTO(it) }
    }

    fun createEncordado(encordado: EncordadoDTO): EncordadoDTO {
        val tarea = Tarea(
            id = encordado.id,
            raqueta = encordado.raqueta,
            precio = encordado.precio,
            user = encordado.user,
            tipoTarea = TipoTarea.ENCORDADO
        )
        tareaRepo.create(tarea)
        return mapper.toEncordadoDTO(this.insert(mapper.fromEncordadoDTO(encordado)))
    }

    fun deleteEncordado(encordado: EncordadoDTO): Boolean {
        return this.delete(mapper.fromEncordadoDTO(encordado))
    }


    fun createEncordadoInit(encordado: EncordadoDTO): EncordadoDTO {
        val tarea = Tarea(
            id = encordado.id,
            raqueta = encordado.raqueta,
            precio = encordado.precio,
            user = encordado.user,
            tipoTarea = TipoTarea.ENCORDADO
        )
        tareaRepo.insert(tarea)
        return mapper.toEncordadoDTO(repository.insert(mapper.fromEncordadoDTO(encordado)))
    }
}