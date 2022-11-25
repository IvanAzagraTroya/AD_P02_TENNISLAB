package repositories.personalizacion

import entities.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromMaquinaDaoToMaquina
import mappers.fromPersonalizacionDaoToPersonalizacion
import models.Maquina
import models.Personalizacion
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PersonalizacionRepositoryImpl(
    private val personalizacionDao: UUIDEntityClass<PersonalizacionDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>
): IPersonalizacionRepository {
    override suspend fun readAll(): Flow<Personalizacion> = newSuspendedTransaction(Dispatchers.IO) {
        personalizacionDao.all().map { it.fromPersonalizacionDaoToPersonalizacion(tareaDao)}.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Personalizacion?> = suspendedTransactionAsync(Dispatchers.IO) {
        personalizacionDao.findById(id)?.fromPersonalizacionDaoToPersonalizacion(tareaDao)
    }

    override suspend fun create(entity: Personalizacion): Deferred<Personalizacion> = suspendedTransactionAsync(Dispatchers.IO) {
        val exists = personalizacionDao.findById(entity.id)
        exists?.let { update(entity, exists) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Personalizacion, exists: PersonalizacionDao): Personalizacion {
        return exists.apply {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(entity.raqueta, entity.user)
    }

    suspend fun insert(entity: Personalizacion): Personalizacion {
        return personalizacionDao.new(entity.id) {
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizacionDaoToPersonalizacion(entity.raqueta, entity.user)
    }

    override suspend fun delete(entity: Personalizacion): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = personalizacionDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}