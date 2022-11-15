package mappers

import dto.TurnoDTO
import entities.TurnoDao
import models.Tarea
import models.Turno
import repositories.tarea.TareaRepositoryImpl

fun TurnoDao.fromTurnoDaoToTurno(): Turno {
    val tareaRepo = TareaRepositoryImpl()
    return Turno(
        id = id.value,
        worker = worker.fromUserDaoToUser(),
        maquina = maquina.fromMaquinaDaoToMaquina(),
        horaInicio = horaInicio,
        horaFin = horaFin,
        numPedidosActivos = numPedidosActivos,
        tarea1 = if (tarea1==null) null else tareaRepo.getById(tarea1),
        tarea2 = if (tarea2==null) null else tareaRepo.getById(tarea2)
    )
}

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