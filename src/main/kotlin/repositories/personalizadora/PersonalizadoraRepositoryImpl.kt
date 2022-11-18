package repositories.personalizadora

import entities.MaquinaDao
import entities.PersonalizadoraDao
import mappers.fromPersonalizadoraDaoToPersonalizadora
import models.Personalizadora
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

    override fun create(entity: Personalizadora): Personalizadora = transaction {
        val existe = personalizadoraDao.findById(entity.id)
        existe?.let { update(entity, it) } ?: run { insert(entity) }
    }

    private fun insert(entity: Personalizadora): Personalizadora {
        return personalizadoraDao.new(entity.id) {
            measuresManeuverability = entity.measuresManeuverability
            measuresBalance = entity.measuresBalance
            measuresRigidity = entity.measuresRigidity
        }.fromPersonalizadoraDaoToPersonalizadora(maquinaDao)
    }

    private fun update(entity: Personalizadora, existe: PersonalizadoraDao): Personalizadora {
        return existe.apply {
            measuresManeuverability = entity.measuresManeuverability
            measuresBalance = entity.measuresBalance
            measuresRigidity = entity.measuresRigidity
        }.fromPersonalizadoraDaoToPersonalizadora(maquinaDao)
    }

    override fun delete(entity: Personalizadora): Boolean = transaction {
        val existe = personalizadoraDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}