package services

import dto.PedidoDTO
import entities.*
import kotlinx.coroutines.flow.toList
import mappers.PedidoMapper
import models.Pedido
import repositories.pedidos.PedidoRepositoryImpl
import java.util.UUID

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Pedido.
 */
class PedidoService : BaseService<Pedido, UUID, PedidoRepositoryImpl>(PedidoRepositoryImpl(
    PedidoDao, UserDao
)) {
    val mapper = PedidoMapper()

    suspend fun getAllPedidos(): List<PedidoDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getPedidoById(id: UUID): PedidoDTO? {
        return this.findById(id).await()?.let { mapper.toDTO(it) }
    }

    suspend fun createPedido(pedido: PedidoDTO): PedidoDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(pedido)).await())
    }

    suspend fun deletePedido(pedido: PedidoDTO): Boolean {
        return this.delete(mapper.fromDTO(pedido)).await()
    }
}