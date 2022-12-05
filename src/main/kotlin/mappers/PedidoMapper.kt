package mappers

import dto.PedidoDTO
import entities.*
import models.Pedido
import models.Tarea
import models.Turno
import models.enums.PedidoEstado

/**
 * @author Iván Azagra Troya
 *
 * Función que recoge las entidades DAO
 * para devolver las entidades de la clase User
 */
fun PedidoDao.fromPedidoDaoToPedido(): Pedido {
    val listTareas = mutableListOf<Tarea>()
    val listTurnos = mutableListOf<Turno>()
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

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase que hereda de BaseMapper y se encarga de pasar de
 * DTO a Modelo y de Modelo a DTO.
 */
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