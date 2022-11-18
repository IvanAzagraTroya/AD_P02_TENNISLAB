package mappers

import dto.TurnoDTO
import entities.*
import models.Tarea
import models.Turno
import org.jetbrains.exposed.dao.UUIDEntityClass
import repositories.tarea.TareaRepositoryImpl

fun TurnoDao.fromTurnoDaoToTurno(
    tareaDao: UUIDEntityClass<TareaDao>,
    productoDao: UUIDEntityClass<ProductoDao>,
    userDao: UUIDEntityClass<UserDao>,
    maquinaDao: UUIDEntityClass<MaquinaDao>
): Turno {
    return Turno(
        id = id.value,
        worker = worker.fromUserDaoToUser(),
        maquina = maquina.fromMaquinaDaoToMaquina(maquinaDao),
        horaInicio = horaInicio,
        horaFin = horaFin,
        tarea1 = tarea1?.fromTareaDaoToTarea(tareaDao, productoDao, userDao),
        tarea2 = tarea2?.fromTareaDaoToTarea(tareaDao, productoDao, userDao)
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