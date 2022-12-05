package services

import dto.EncordadoDTO
import dto.TareaDTO
import entities.*
import kotlinx.coroutines.flow.toList
import mappers.TareaMapper
import models.Encordado
import models.Tarea
import models.enums.TipoTarea
import repositories.encordado.EncordadoRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import java.util.UUID

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Encordado.
 */
class EncordadoService: BaseService<Encordado, UUID, EncordadoRepositoryImpl>(
    EncordadoRepositoryImpl(
        TareaDao, ProductoDao, EncordadoDao
)) {
    val tareaRepo = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    val mapper = TareaMapper()

    suspend fun getAllEncordados(): List<EncordadoDTO> {
        return mapper.toEncordadoDTO(this.findAll().toList())
    }

    suspend fun getEncordadoById(id: UUID): EncordadoDTO? {
        return this.findById(id).await()?.let { mapper.toEncordadoDTO(it) }
    }

    suspend fun createEncordado(encordado: EncordadoDTO): TareaDTO {
        val tarea = Tarea(
            id = encordado.id,
            raqueta = encordado.raqueta,
            precio = encordado.precio,
            user = encordado.user,
            tipoTarea = TipoTarea.ENCORDADO
        )
        tareaRepo.create(tarea).await()
        return mapper.toDTO(this.insert(mapper.fromEncordadoDTO(encordado)).await())
    }

    suspend fun deleteEncordado(encordado: EncordadoDTO): Boolean {
        return this.delete(mapper.fromEncordadoDTO(encordado)).await()
    }
}