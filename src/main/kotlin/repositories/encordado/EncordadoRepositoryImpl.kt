package repositories.encordado

import entities.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromEncordadoDaoToEncordado
import models.Encordado
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class EncordadoRepositoryImpl(
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val encordadoDao: UUIDEntityClass<EncordadoDao>
):IEncordadoRepository {
    override suspend fun readAll(): Flow<Encordado> = newSuspendedTransaction(Dispatchers.IO) {
        encordadoDao.all().map { it.fromEncordadoDaoToEncordado(tareaDao) }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Encordado?> = suspendedTransactionAsync(Dispatchers.IO) {
        encordadoDao.findById(id)?.fromEncordadoDaoToEncordado(tareaDao)
    }

    override suspend fun create(entity: Encordado): Deferred<Encordado> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = encordadoDao.findById(entity.id)
        existe?.let { update(entity, it) } ?: run { insert(entity) }
    }

    fun insert(entity: Encordado): Encordado {
        return encordadoDao.new(entity.id) {
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeHorizontal = productoDao.findById(entity.cordajeHorizontal.id) ?: throw Exception()
            cordajeVertical = productoDao.findById(entity.cordajeVertical.id) ?: throw Exception()
            dosNudos = entity.dosNudos
            precio = entity.precio
        }.fromEncordadoDaoToEncordado(entity.raqueta, entity.user)
    }

    private fun update(entity: Encordado, existe: EncordadoDao): Encordado {
        return existe.apply {
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeHorizontal = productoDao.findById(entity.cordajeHorizontal.id) ?: throw Exception()
            cordajeVertical = productoDao.findById(entity.cordajeVertical.id) ?: throw Exception()
            dosNudos = entity.dosNudos
            precio = entity.precio
        }.fromEncordadoDaoToEncordado(entity.raqueta, entity.user)
    }

    override suspend fun delete(entity: Encordado): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = encordadoDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}