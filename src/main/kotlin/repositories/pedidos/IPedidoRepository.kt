package repositories.pedidos

import models.Pedido
import repositories.ICRUDRepository
import java.util.UUID

interface IPedidoRepository : ICRUDRepository<Pedido, UUID> {
}