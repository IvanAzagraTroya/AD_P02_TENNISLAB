package repositories.adquisicion

import entities.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromAdquisicionDaoToAdquisicion
import models.Adquisicion
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de hacer las operaciones CRUD en la base de datos.
 * Implementa ICRUDRepository.
 */
class AdquisicionRepositoryImpl(
    private val adquisicionDao: UUIDEntityClass<AdquisicionDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>
): IAdquisicionRepository {
    override suspend fun create(entity: Adquisicion): Deferred<Adquisicion> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = adquisicionDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    fun insert(entity: Adquisicion): Adquisicion {
        return adquisicionDao.new(entity.id) {
            productoAdquirido = productoDao.findById(entity.productoAdquirido.id) ?: throw Exception()
            precio = entity.precio
        }.fromAdquisicionDaoToAdquisicion(entity.raqueta, entity.user)
    }

    private fun update(entity: Adquisicion, existe: AdquisicionDao): Adquisicion {
        return existe.apply {
            productoAdquirido = productoDao.findById(entity.productoAdquirido.id) ?: throw Exception()
            precio = entity.precio
        }.fromAdquisicionDaoToAdquisicion(entity.raqueta, entity.user)
    }

    override suspend fun readAll(): Flow<Adquisicion> = newSuspendedTransaction(Dispatchers.IO) {
        adquisicionDao.all().map { it.fromAdquisicionDaoToAdquisicion(tareaDao) }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Adquisicion?> = suspendedTransactionAsync(Dispatchers.IO) {
        adquisicionDao.findById(id)?.fromAdquisicionDaoToAdquisicion(tareaDao)
    }

    override suspend fun delete(entity: Adquisicion): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = adquisicionDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}