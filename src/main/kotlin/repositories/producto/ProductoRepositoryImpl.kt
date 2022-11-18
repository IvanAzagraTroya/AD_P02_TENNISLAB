package repositories.producto

import entities.ProductoDao
import mappers.fromProductoDaoToProducto
import models.Producto
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ProductoRepositoryImpl(
    private val productoDao: UUIDEntityClass<ProductoDao>
): IProductoRepository {
    override fun readAll(): List<Producto> = transaction {
        productoDao.all().map { it.fromProductoDaoToProducto() }
    }

    override fun findById(id: UUID): Producto? = transaction {
        productoDao.findById(id)?.fromProductoDaoToProducto()
    }

    override fun create(entity: Producto): Producto = transaction {
        val existe = productoDao.findById(entity.id)
        existe?.let { update(entity, existe) }
            ?: run { insert(entity) }
    }

    private fun insert(entity: Producto) : Producto {
        return productoDao.new(entity.id) {
            tipoProducto = entity.tipoProducto.toString()
            marca = entity.marca
            modelo = entity.modelo
            precio = entity.precio
            stock = entity.stock
        }.fromProductoDaoToProducto()
    }

    private fun update(entity: Producto, existe: ProductoDao): Producto {
        return existe.apply {
            tipoProducto = entity.tipoProducto.toString()
            marca = entity.marca
            modelo = entity.modelo
            precio = entity.precio
            stock = entity.stock
        }.fromProductoDaoToProducto()
    }

    override fun delete(entity: Producto): Boolean = transaction {
        val existe = productoDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}