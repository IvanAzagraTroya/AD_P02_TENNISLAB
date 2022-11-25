package repositories.producto

import entities.ProductoDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromProductoDaoToProducto
import models.Producto
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ProductoRepositoryImpl(
    private val productoDao: UUIDEntityClass<ProductoDao>
): IProductoRepository {
    override suspend fun readAll(): Flow<Producto> = newSuspendedTransaction(Dispatchers.IO) {
        productoDao.all().map { it.fromProductoDaoToProducto() }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Producto?> = suspendedTransactionAsync(Dispatchers.IO) {
        productoDao.findById(id)?.fromProductoDaoToProducto()
    }

    override suspend fun create(entity: Producto): Deferred<Producto> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = productoDao.findById(entity.id)
        existe?.let { update(entity, existe) }
            ?: run { insert(entity) }
    }

    fun insert(entity: Producto) : Producto {
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

    override suspend fun delete(entity: Producto): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = productoDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}