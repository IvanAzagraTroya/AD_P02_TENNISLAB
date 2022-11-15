package mappers

import entities.PedidoDao
import models.Pedido

/**
 * @author Iván Azagra Troya
 * Archivo que sirve para mapear los objetos DAO a modelos POKO
 */

// TODO revisar lo que se le pasa desde el dao al pedido

fun PedidoDao.fromPedidoDaoToPedido(): Pedido {
    return Pedido(
        id = id.value,
        tareas = tareas,
        client = client,
        turnos = turnos,
        state = state,
        fechaEntrada = fechaEntrada,
        fechaProgramada = fechaProgramada,
        fechaSalida = fechaSalida,
        fechaEntrega = fechaEntrega
    )
}