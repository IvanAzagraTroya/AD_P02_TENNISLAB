package mappers

import entities.TurnoDao
import models.Tarea
import models.Turno

fun TurnoDao.fromTurnoDaoToTurno(): Turno {
    return Turno(
        id = id.value,
        worker = worker.fromUserDaoToUser(),
        maquina = maquina.fromMaquinaDaoToMaquina(),
        horaInicio = horaInicio,
        horaFin = horaFin,
        numPedidosActivos = numPedidosActivos,
        tarea1 = TareaRepository().getById(tarea1),
        tarea2 = TareaRepository().getById(tarea2)
    )
}

class TurnoMapper {
}