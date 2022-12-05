package repositories.turno

import entities.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromTurnoDaoToTurno
import models.Turno
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
class TurnoRepositoryImpl(
    private val turnoDao: UUIDEntityClass<TurnoDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>
): ITurnoRepository {
    override suspend fun readAll(): Flow<Turno> = newSuspendedTransaction(Dispatchers.IO) {
        turnoDao.all().map { it.fromTurnoDaoToTurno() }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Turno?> = suspendedTransactionAsync(Dispatchers.IO) {
        turnoDao.findById(id)?.fromTurnoDaoToTurno()
    }

    override suspend fun create(entity: Turno): Deferred<Turno> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = turnoDao.findById(entity.id)
        existe?.let { update(entity, existe) }
            ?: run { insert(entity) }
    }

    private suspend fun update(entity: Turno, existe: TurnoDao): Turno {
        return existe.apply {
            worker = userDao.findById(entity.worker.id) ?: throw Exception()
            maquina = maquinaDao.findById(entity.maquina.id) ?: throw Exception()
            horaInicio = entity.horaInicio
            horaFin = entity.horaFin
            numPedidosActivos = entity.numPedidosActivos
            tarea1 = tareaDao.findById(entity.tarea1.id) ?: throw Exception()
            tarea2 = entity.tarea2?.id?.let { tareaDao.findById(it) }
        }.fromTurnoDaoToTurno()
    }

    suspend fun insert(entity: Turno): Turno {
        return turnoDao.new(entity.id) {
            worker = userDao.findById(entity.worker.id) ?: throw Exception()
            maquina = maquinaDao.findById(entity.maquina.id) ?: throw Exception()
            horaInicio = entity.horaInicio
            horaFin = entity.horaFin
            numPedidosActivos = entity.numPedidosActivos
            tarea1 = tareaDao.findById(entity.tarea1.id) ?: throw Exception()
            tarea2 = entity.tarea2?.id?.let { tareaDao.findById(it) }
        }.fromTurnoDaoToTurno()
    }

    override suspend fun delete(entity: Turno): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = turnoDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}