package repositories.maquina

import entities.EncordadoraDao
import entities.MaquinaDao
import entities.PersonalizadoraDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromMaquinaDaoToMaquina
import models.Encordadora
import models.Maquina
import models.Personalizadora
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.encordadora.EncordadoraRepositoryImpl
import repositories.personalizadora.PersonalizadoraRepositoryImpl
import java.util.*

class MaquinaRepositoryImpl(
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IMaquinaRepository {
    override suspend fun readAll(): Flow<Maquina> = newSuspendedTransaction(Dispatchers.IO) {
        maquinaDao.all().map { it.fromMaquinaDaoToMaquina(maquinaDao) }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Maquina?> = suspendedTransactionAsync(Dispatchers.IO) {
        val mdao = maquinaDao.findById(id)
        mdao?.fromMaquinaDaoToMaquina(maquinaDao)
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
        /*
        when (entity) {
            is Personalizadora -> pRepo.create(entity).await()
            is Encordadora -> eRepo.create(entity).await()
        }
        return maquina.fromMaquinaDaoToMaquina(maquinaDao, perDao, encDao)

         */
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
        /*
        when (entity) {
            is Personalizadora -> pRepo.create(entity).await()
            is Encordadora -> eRepo.create(entity).await()
        }
        return maquina.fromMaquinaDaoToMaquina(maquinaDao, perDao, encDao)

         */
        return entity
    }

    override suspend fun delete(entity: Maquina): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = maquinaDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}