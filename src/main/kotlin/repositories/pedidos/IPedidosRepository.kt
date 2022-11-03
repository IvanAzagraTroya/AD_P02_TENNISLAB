package repositories.pedidos

import models.Pedido
import repositories.ICRUDRepository
import java.util.UUID

interface IPedidosRepository : ICRUDRepository<Pedido, UUID> {

}