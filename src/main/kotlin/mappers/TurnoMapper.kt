package mappers

import dto.TurnoDTO
import entities.*
import models.Turno

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Función que recoge las entidades DAO
 * para devolver las entidades de la clase User
 */
suspend fun TurnoDao.fromTurnoDaoToTurno(): Turno {
    return Turno(
        id = id.value,
        worker = worker.fromUserDaoToUser(),
        maquina = maquina.fromMaquinaDaoToMaquina(),
        horaInicio = horaInicio,
        horaFin = horaFin,
        tarea1 = tarea1.fromTareaDaoToTarea(),
        tarea2 = tarea2?.fromTareaDaoToTarea()
    )
}

/**
 * @author Ivan Azagra Troya
 *
 * Clase que hereda de BaseMapper y se encarga de pasar de
 * DTO a Modelo y de Modelo a DTO.
 */
class TurnoMapper: BaseMapper<Turno, TurnoDTO>() {
    override fun fromDTO(item: TurnoDTO): Turno {
        return Turno(
            id = item.id,
            worker = item.worker,
            maquina = item.maquina,
            horaInicio = item.horaInicio,
            horaFin = item.horaFin,
            tarea1 = item.tarea1,
            tarea2 = item.tarea2
        )
    }

    override fun toDTO(item: Turno): TurnoDTO {
        return TurnoDTO(
            id = item.id,
            worker = item.worker,
            maquina = item.maquina,
            horaInicio = item.horaInicio,
            horaFin = item.horaFin,
            tarea1 = item.tarea1,
            tarea2 = item.tarea2
        )
    }
}