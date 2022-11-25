package repositories.encordadora

import entities.EncordadoraDao
import entities.MaquinaDao
import entities.PersonalizadoraDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromEncordadoraDaoToEncordadora
import models.Encordadora
import models.enums.TipoMaquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class EncordadoraRepositoryImpl(
    private val encordadoraDao: UUIDEntityClass<EncordadoraDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IEncordadoraRepository {
    override suspend fun readAll(): Flow<Encordadora> = newSuspendedTransaction(Dispatchers.IO) {
        encordadoraDao.all().map { it.fromEncordadoraDaoToEncordadora(maquinaDao)}.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Encordadora?> = suspendedTransactionAsync(Dispatchers.IO) {
        encordadoraDao.findById(id)?.fromEncordadoraDaoToEncordadora(maquinaDao)
    }

    override suspend fun create(entity: Encordadora): Deferred<Encordadora> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = encordadoraDao.findById(entity.id)
        existe?.let { update(entity, it) } ?: run { insert(entity) }
    }

    fun insert(entity: Encordadora): Encordadora {
        return encordadoraDao.new(entity.id) {
            isManual = entity.isManual
            maxTension = entity.maxTension
            minTension = entity.minTension
        }.fromEncordadoraDaoToEncordadora(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    private fun update(entity: Encordadora, existe: EncordadoraDao): Encordadora {
        return existe.apply {
            isManual = entity.isManual
            maxTension = entity.maxTension
            minTension = entity.minTension
        }.fromEncordadoraDaoToEncordadora(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    override suspend fun delete(entity: Encordadora): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = encordadoraDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        val maquina = maquinaDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        if (TipoMaquina.parseTipoMaquina(maquina.tipoMaquina) != TipoMaquina.ENCORDADORA)
            return@suspendedTransactionAsync false
        else {
            existe.delete()
            maquina.delete()
            true
        }
    }
}