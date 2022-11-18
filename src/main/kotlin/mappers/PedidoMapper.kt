package mappers

import dto.PedidoDTO
import entities.*
import models.Pedido
import models.Tarea
import models.Turno
import models.enums.PedidoEstado
import org.jetbrains.exposed.dao.UUIDEntityClass

fun PedidoDao.fromPedidoDaoToPedido(
    tareaDao: UUIDEntityClass<TareaDao>,
    productoDao: UUIDEntityClass<ProductoDao>,
    userDao: UUIDEntityClass<UserDao>,
    maquinaDao: UUIDEntityClass<MaquinaDao>
): Pedido {
    val listTareas = mutableListOf<Tarea>()
    tareas.forEach { listTareas.add(it.fromTareaDaoToTarea(tareaDao, productoDao, userDao)) }
    val listTurnos = mutableListOf<Turno>()
    turnos.forEach { listTurnos.add(it.fromTurnoDaoToTurno(tareaDao, productoDao, userDao, maquinaDao)) }
    return Pedido(
        id = id.value,
        tareas = listTareas.toList(),
        client = client.fromUserDaoToUser(),
        turnos = listTurnos.toList(),
        state = PedidoEstado.parseTipoEstado(state),
        fechaEntrada = fechaEntrada,
        fechaProgramada = fechaProgramada,
        fechaSalida = fechaSalida,
        fechaEntrega = fechaEntrega
    )
}

class PedidoMapper: BaseMapper<Pedido, PedidoDTO>() {
    override fun fromDTO(item: PedidoDTO): Pedido {
        return Pedido(
            id = item.id,
            tareas = item.tareas,
            client = item.client,
            turnos = item.turnos,
            state = item.state,
            fechaEntrada = item.fechaEntrada,
            fechaProgramada = item.fechaProgramada,
            fechaSalida = item.fechaSalida,
            fechaEntrega = item.fechaEntrega
        )
    }

    override fun toDTO(item: Pedido): PedidoDTO {
        return PedidoDTO(
            id = item.id,
            tareas = item.tareas,
            client = item.client,
            turnos = item.turnos,
            state = item.state,
            fechaEntrada = item.fechaEntrada,
            fechaProgramada = item.fechaProgramada,
            fechaSalida = item.fechaSalida,
            fechaEntrega = item.fechaEntrega
        )
    }
}