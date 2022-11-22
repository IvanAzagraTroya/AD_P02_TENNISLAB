package repositories.maquina

import entities.MaquinaDao
import mappers.fromMaquinaDaoToMaquina
import models.Maquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class MaquinaRepositoryImpl(
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IMaquinaRepository {
    override fun readAll(): List<Maquina> = transaction {
        maquinaDao.all().map { it.fromMaquinaDaoToMaquina(
            it.modelo,
            it.marca,
            it.fechaAdquisicion,
            it.numeroSerie
        ) }
    }

    override fun findById(id: UUID): Maquina? = transaction {
        val mdao = maquinaDao.findById(id)
        mdao?.fromMaquinaDaoToMaquina(
            mdao.modelo,
            mdao.marca,
            mdao.fechaAdquisicion,
            mdao.numeroSerie
        )
    }

    override fun create(entity: Maquina): Maquina {
        val exists = maquinaDao.findById(entity.id)
        return exists?.let { update(entity, exists) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Maquina, exists: MaquinaDao): Maquina = transaction {
        exists.apply {
            modelo = entity.modelo
            marca = entity.marca
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            tipoMaquina = entity.tipoMaquina.toString()
        }.fromMaquinaDaoToMaquina(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    fun insert(entity: Maquina): Maquina = transaction {
        maquinaDao.new(entity.id) {
            modelo = entity.modelo
            marca = entity.marca
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            tipoMaquina = entity.tipoMaquina.toString()
        }.fromMaquinaDaoToMaquina(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    override fun delete(entity: Maquina): Boolean = transaction {
        val existe = maquinaDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}