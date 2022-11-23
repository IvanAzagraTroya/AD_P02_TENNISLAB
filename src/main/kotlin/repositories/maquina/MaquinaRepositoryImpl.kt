package repositories.maquina

import entities.MaquinaDao
import kotlinx.coroutines.Dispatchers
import mappers.fromMaquinaDaoToMaquina
import models.Maquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class MaquinaRepositoryImpl(
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IMaquinaRepository {
    override suspend fun readAll(): List<Maquina> = newSuspendedTransaction(Dispatchers.IO) {
        maquinaDao.all().map { it.fromMaquinaDaoToMaquina(
            it.modelo,
            it.marca,
            it.fechaAdquisicion,
            it.numeroSerie
        ) }
    }

    override suspend fun findById(id: UUID): Maquina? = newSuspendedTransaction(Dispatchers.IO) {
        val mdao = maquinaDao.findById(id)
        mdao?.fromMaquinaDaoToMaquina(
            mdao.modelo,
            mdao.marca,
            mdao.fechaAdquisicion,
            mdao.numeroSerie
        )
    }

    override suspend fun create(entity: Maquina): Maquina = newSuspendedTransaction(Dispatchers.IO) {
        val exists = maquinaDao.findById(entity.id)
        exists?.let { update(entity, exists) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Maquina, exists: MaquinaDao): Maquina {
        return exists.apply {
            modelo = entity.modelo
            marca = entity.marca
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            tipoMaquina = entity.tipoMaquina.toString()
        }.fromMaquinaDaoToMaquina(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    fun insert(entity: Maquina): Maquina {
        return maquinaDao.new(entity.id) {
            modelo = entity.modelo
            marca = entity.marca
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            tipoMaquina = entity.tipoMaquina.toString()
        }.fromMaquinaDaoToMaquina(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    override suspend fun delete(entity: Maquina): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val existe = maquinaDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}