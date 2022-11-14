package repositories.producto

import models.Producto
import repositories.ICRUDRepository
import java.util.UUID

interface IProductoRepository: ICRUDRepository<Producto, UUID> {
}