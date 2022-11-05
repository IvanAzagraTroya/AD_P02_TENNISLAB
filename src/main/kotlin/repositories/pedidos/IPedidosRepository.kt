package repositories.pedidos

import models.Pedido
import models.User
import repositories.ICRUDRepository
import java.util.UUID

interface IPedidosRepository : ICRUDRepository<Pedido, UUID> {
    fun findById(id: UUID): Pedido?
}