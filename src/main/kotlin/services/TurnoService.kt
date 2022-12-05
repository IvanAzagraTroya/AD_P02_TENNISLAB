package services

import dto.TurnoDTO
import entities.*
import kotlinx.coroutines.flow.toList
import mappers.TurnoMapper
import models.Turno
import repositories.turno.TurnoRepositoryImpl
import java.util.UUID

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Turno.
 */
class TurnoService: BaseService<Turno, UUID, TurnoRepositoryImpl>(
    TurnoRepositoryImpl(
    TurnoDao, UserDao, MaquinaDao, TareaDao
)) {
    val mapper = TurnoMapper()

    suspend fun getAllTurnos(): List<TurnoDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getTurnoById(id: UUID): TurnoDTO? {
        return this.findById(id).await()?.let { mapper.toDTO(it) }
    }

    suspend fun createTurno(turno: TurnoDTO): TurnoDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(turno)).await())
    }

    suspend fun deleteTurno(turno: TurnoDTO): Boolean {
        return this.delete(mapper.fromDTO(turno)).await()
    }
}