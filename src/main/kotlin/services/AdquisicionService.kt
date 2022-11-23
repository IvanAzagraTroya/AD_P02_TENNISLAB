package services

import dto.AdquisicionDTO
import entities.AdquisicionDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import mappers.TareaMapper
import models.Adquisicion
import models.Tarea
import models.enums.TipoTarea
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import java.util.UUID

class AdquisicionService: BaseService<Adquisicion, UUID, AdquisicionRepositoryImpl>(
    AdquisicionRepositoryImpl(
        AdquisicionDao, TareaDao, ProductoDao, UserDao
)) {
    val tareaRepo = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    val mapper = TareaMapper()

    suspend fun getAllAdquisiciones(): List<AdquisicionDTO> {
        return mapper.toAdquisicionDTO(this.findAll())
    }

    suspend fun getAdquisicionById(id: UUID): AdquisicionDTO? {
        return this.findById(id)?.let { mapper.toAdquisicionDTO(it) }
    }

    suspend fun createAdquisicion(adquisicion: AdquisicionDTO): AdquisicionDTO {
        val tarea = Tarea(
            id = adquisicion.id,
            raqueta = adquisicion.raqueta,
            precio = adquisicion.precio,
            user = adquisicion.user,
            tipoTarea = TipoTarea.ADQUISICION
        )
        tareaRepo.create(tarea)
        return mapper.toAdquisicionDTO(this.insert(mapper.fromAdquisicionDTO(adquisicion)))
    }

    suspend fun deleteAdquisicion(adquisicion: AdquisicionDTO): Boolean {
        return this.delete(mapper.fromAdquisicionDTO(adquisicion))
    }
}