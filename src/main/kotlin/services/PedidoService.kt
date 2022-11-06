package services

import dto.PedidoDTO
import models.Pedido
import repositories.pedidos.PedidosRepositoryImpl
import java.util.UUID

class PedidoService : BaseService<Pedido, UUID, PedidosRepositoryImpl>(PedidosRepositoryImpl()) {
    //private val mapper: PedidoMapper()

    fun getAllPedidos(): List<PedidoDTO> {
        TODO("HACER EL PedidoMapper")
    }

    fun getPedidoById(id: UUID): PedidoDTO {
        TODO("HACER EL PedidoMapper")
    }

    fun createPedido(t: PedidoDTO): PedidoDTO {
        TODO("HACER EL PedidoMapper")
    }

    fun updatePedido(t: PedidoDTO): PedidoDTO {
        TODO("HACER EL PedidoMapper")
    }

    fun deletePedido(t: PedidoDTO): PedidoDTO {
        TODO("HACER EL PedidoMapper")
    }
}