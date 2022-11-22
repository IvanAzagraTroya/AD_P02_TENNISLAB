package repositories.encordadora

import entities.EncordadoraDao
import entities.MaquinaDao
import mappers.fromEncordadoraDaoToEncordadora
import models.Encordadora
import models.enums.TipoMaquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class EncordadoraRepositoryImpl(
    private val encordadoraDao: UUIDEntityClass<EncordadoraDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IEncordadoraRepository {
    override fun readAll(): List<Encordadora> = transaction {
        encordadoraDao.all().map { it.fromEncordadoraDaoToEncordadora(maquinaDao)}
    }

    override fun findById(id: UUID): Encordadora? = transaction {
        encordadoraDao.findById(id)?.fromEncordadoraDaoToEncordadora(maquinaDao)}

    override fun create(entity: Encordadora): Encordadora {
        val existe = encordadoraDao.findById(entity.id)
        return existe?.let { update(entity, it) } ?: run { insert(entity) }
    }

    fun insert(entity: Encordadora): Encordadora = transaction {
        encordadoraDao.new(entity.id) {
            isManual = entity.isManual
            maxTension = entity.maxTension
            minTension = entity.minTension
        }.fromEncordadoraDaoToEncordadora(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    private fun update(entity: Encordadora, existe: EncordadoraDao): Encordadora = transaction {
        existe.apply {
            isManual = entity.isManual
            maxTension = entity.maxTension
            minTension = entity.minTension
        }.fromEncordadoraDaoToEncordadora(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    override fun delete(entity: Encordadora): Boolean = transaction {
        val existe = encordadoraDao.findById(entity.id) ?: return@transaction false
        val maquina = maquinaDao.findById(entity.id) ?: return@transaction false
        if (TipoMaquina.parseTipoMaquina(maquina.tipoMaquina) != TipoMaquina.ENCORDADORA)
            return@transaction false
        else {
            maquina.delete()
            existe.delete()
            true
        }
    }
}