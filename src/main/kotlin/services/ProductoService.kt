package services

import dto.ProductoDTO
import entities.ProductoDao
import kotlinx.coroutines.flow.toList
import mappers.ProductoMapper
import models.Producto
import repositories.producto.ProductoRepositoryImpl
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Producto.
 */
class ProductoService: BaseService<Producto, UUID, ProductoRepositoryImpl>(ProductoRepositoryImpl(ProductoDao)) {
    val mapper = ProductoMapper()

    suspend fun getAllProductos(): List<ProductoDTO> {
        return mapper.toDTO(this.findAll().toList())
    }

    suspend fun getProductoById(id: UUID): ProductoDTO? {
        return this.findById(id).await()?.let { mapper.toDTO(it) }
    }

    suspend fun createProducto(user: ProductoDTO): ProductoDTO {
        return mapper.toDTO(this.insert(mapper.fromDTO(user)).await())
    }

    suspend fun deleteProducto(user: ProductoDTO): Boolean {
        return this.delete(mapper.fromDTO(user)).await()
    }
}