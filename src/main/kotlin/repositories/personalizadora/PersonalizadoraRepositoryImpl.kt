package repositories.personalizadora

import entities.MaquinaDao
import entities.PersonalizadoraDao
import mappers.fromPersonalizadoraDaoToPersonalizadora
import models.Personalizadora
import models.enums.TipoMaquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PersonalizadoraRepositoryImpl(
    private val personalizadoraDao: UUIDEntityClass<PersonalizadoraDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>
): IPersonalizadoraRepository {
    override fun readAll(): List<Personalizadora> = transaction {
        personalizadoraDao.all().map { it.fromPersonalizadoraDaoToPersonalizadora(maquinaDao)}
    }

    override fun findById(id: UUID): Personalizadora? = transaction {
        personalizadoraDao.findById(id)?.fromPersonalizadoraDaoToPersonalizadora(maquinaDao)
    }

    override fun create(entity: Personalizadora): Personalizadora {
        val existe = personalizadoraDao.findById(entity.id)
        return existe?.let { update(entity, it) } ?: run { insert(entity) }
    }

    fun insert(entity: Personalizadora): Personalizadora = transaction {
        personalizadoraDao.new(entity.id) {
            measuresManeuverability = entity.measuresManeuverability
            measuresBalance = entity.measuresBalance
            measuresRigidity = entity.measuresRigidity
        }.fromPersonalizadoraDaoToPersonalizadora(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    private fun update(entity: Personalizadora, existe: PersonalizadoraDao): Personalizadora = transaction {
        existe.apply {
            measuresManeuverability = entity.measuresManeuverability
            measuresBalance = entity.measuresBalance
            measuresRigidity = entity.measuresRigidity
        }.fromPersonalizadoraDaoToPersonalizadora(entity.modelo, entity.marca, entity.fechaAdquisicion, entity.numeroSerie)
    }

    override fun delete(entity: Personalizadora): Boolean = transaction {
        val existe = personalizadoraDao.findById(entity.id) ?: return@transaction false
        val maquina = maquinaDao.findById(entity.id) ?: return@transaction false
        if (TipoMaquina.parseTipoMaquina(maquina.tipoMaquina) != TipoMaquina.PERSONALIZADORA)
            return@transaction false
        else {
            maquina.delete()
            existe.delete()
            true
        }
    }
}