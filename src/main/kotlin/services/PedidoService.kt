package services

import dto.PedidoDTO
import entities.*
import mappers.PedidoMapper
import models.Pedido
import repositories.pedidos.PedidoRepositoryImpl
import java.util.UUID

class PedidoService : BaseService<Pedido, UUID, PedidoRepositoryImpl>(PedidoRepositoryImpl(
    PedidoDao, UserDao, MaquinaDao, ProductoDao, TareaDao
)) {
    val mapper = PedidoMapper()

    suspend fun getAllPedidos(): List<PedidoDTO> {
        return mapper.toDTO(this.findAll())
    }

    suspend fun getPedidoById(id: UUID): PedidoDTO? {
        return this.findById(id)?.let { mapper.toDTO(it) }
    }

    suspend fun createPedido(pedido: PedidoDTO): PedidoDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(pedido)))
    }

    suspend fun deletePedido(pedido: PedidoDTO): Boolean {
        return this.delete(mapper.fromDTO(pedido))
    }
}