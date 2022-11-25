package services

import dto.AdquisicionDTO
import dto.TareaDTO
import entities.*
import kotlinx.coroutines.flow.toList
import mappers.TareaMapper
import models.Adquisicion
import models.Tarea
import models.enums.TipoTarea
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import java.util.UUID
import java.util.stream.Collectors

class AdquisicionService: BaseService<Adquisicion, UUID, AdquisicionRepositoryImpl>(
    AdquisicionRepositoryImpl(
        AdquisicionDao, TareaDao, ProductoDao
)) {
    val tareaRepo = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    val mapper = TareaMapper()

    suspend fun getAllAdquisiciones(): List<AdquisicionDTO> {
        return mapper.toAdquisicionDTO(this.findAll().toList())
    }

    suspend fun getAdquisicionById(id: UUID): AdquisicionDTO? {
        return this.findById(id).await()?.let { mapper.toAdquisicionDTO(it) }
    }

    suspend fun createAdquisicion(adquisicion: AdquisicionDTO): TareaDTO {
        val tarea = Tarea(
            id = adquisicion.id,
            raqueta = adquisicion.raqueta,
            precio = adquisicion.precio,
            user = adquisicion.user,
            tipoTarea = TipoTarea.ADQUISICION
        )
        tareaRepo.create(tarea).await()
        return mapper.toDTO(this.insert(mapper.fromAdquisicionDTO(adquisicion)).await())
    }

    suspend fun deleteAdquisicion(adquisicion: AdquisicionDTO): Boolean {
        return this.delete(mapper.fromAdquisicionDTO(adquisicion)).await()
    }
}