package repositories.maquina

import entities.MaquinaDao
import mappers.fromMaquinaDaoToMaquina
import mappers.fromTareaDaoToTarea
import models.Maquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class MaquinaRepositoryImpl(
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IMaquinaRepository {
    override fun readAll(): List<Maquina> = transaction {
        maquinaDao.all().map { it.fromMaquinaDaoToMaquina() }
    }

    override fun findById(id: UUID): Maquina? = transaction {
        maquinaDao.findById(id)?.fromMaquinaDaoToMaquina()
    }

    override fun create(entity: Maquina): Maquina = transaction {
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
        }.fromMaquinaDaoToMaquina()
    }

    private fun insert(entity: Maquina): Maquina {
        return maquinaDao.new {
            modelo = entity.modelo
            marca = entity.marca
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            tipoMaquina = entity.tipoMaquina.toString()
        }.fromMaquinaDaoToMaquina()
    }

    override fun delete(entity: Maquina): Boolean = transaction {
        val existe = maquinaDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}