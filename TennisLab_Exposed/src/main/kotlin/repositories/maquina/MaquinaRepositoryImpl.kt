package repositories.maquina

import entities.MaquinaDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromMaquinaDaoToMaquina
import models.Maquina
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
class MaquinaRepositoryImpl(
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IMaquinaRepository {
    override suspend fun readAll(): Flow<Maquina> = newSuspendedTransaction(Dispatchers.IO) {
        maquinaDao.all().map { it.fromMaquinaDaoToMaquina() }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Maquina?> = suspendedTransactionAsync(Dispatchers.IO) {
        val mdao = maquinaDao.findById(id)
        mdao?.fromMaquinaDaoToMaquina()
    }

    override suspend fun create(entity: Maquina): Deferred<Maquina> = suspendedTransactionAsync(Dispatchers.IO)  {
        val exists = maquinaDao.findById(entity.id)
        exists?.let { update(entity, exists) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Maquina, exists: MaquinaDao): Maquina {
        exists.apply {
            modelo = entity.modelo
            marca = entity.marca
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            tipoMaquina = entity.tipoMaquina.toString()
        }
        return entity
    }

    fun insert(entity: Maquina): Maquina {
        maquinaDao.new(entity.id) {
            modelo = entity.modelo
            marca = entity.marca
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            tipoMaquina = entity.tipoMaquina.toString()
        }
        return entity
    }

    override suspend fun delete(entity: Maquina): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = maquinaDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}