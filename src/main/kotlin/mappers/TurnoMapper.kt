package mappers

import dto.TurnoDTO
import entities.*
import models.Turno
import org.jetbrains.exposed.dao.UUIDEntityClass

fun TurnoDao.fromTurnoDaoToTurno(
    tareaDao: UUIDEntityClass<TareaDao>,
    productoDao: UUIDEntityClass<ProductoDao>,
    userDao: UUIDEntityClass<UserDao>,
): Turno {
    return Turno(
        id = id.value,
        worker = worker.fromUserDaoToUser(),
        maquina = maquina.fromMaquinaDaoToMaquina(
            maquina.modelo,
            maquina.marca,
            maquina.fechaAdquisicion,
            maquina.numeroSerie
        ),
        horaInicio = horaInicio,
        horaFin = horaFin,
        tarea1 = tarea1?.fromTareaDaoToTarea(
            tarea1!!.raqueta.fromProductoDaoToProducto(),
            tarea1!!.user.fromUserDaoToUser()
        ),
        tarea2 = tarea2?.fromTareaDaoToTarea(
            tarea2!!.raqueta.fromProductoDaoToProducto(),
            tarea2!!.user.fromUserDaoToUser())
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