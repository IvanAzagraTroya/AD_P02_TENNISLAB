package repositories.turno

import entities.*
import kotlinx.coroutines.Dispatchers
import mappers.fromTurnoDaoToTurno
import models.Turno
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TurnoRepositoryImpl(
    private val turnoDao: UUIDEntityClass<TurnoDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>
): ITurnoRepository {
    override suspend fun readAll(): List<Turno> = newSuspendedTransaction(Dispatchers.IO) {
        turnoDao.all().map { it.fromTurnoDaoToTurno(tareaDao, productoDao, userDao) }
    }

    override suspend fun findById(id: UUID): Turno? = newSuspendedTransaction(Dispatchers.IO) {
        turnoDao.findById(id)?.fromTurnoDaoToTurno(tareaDao, productoDao, userDao)
    }

    override suspend fun create(entity: Turno): Turno = newSuspendedTransaction(Dispatchers.IO) {
        val existe = turnoDao.findById(entity.id)
        existe?.let { update(entity, existe) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Turno, existe: TurnoDao): Turno {
        return existe.apply {
            worker = userDao.findById(entity.worker.id) ?: throw Exception()
            maquina = maquinaDao.findById(entity.maquina.id) ?: throw Exception()
            horaInicio = entity.horaInicio
            horaFin = entity.horaFin
            numPedidosActivos = entity.numPedidosActivos
            tarea1 = entity.tarea1?.id?.let { tareaDao.findById(it) }
            tarea2 = entity.tarea2?.id?.let { tareaDao.findById(it) }
        }.fromTurnoDaoToTurno(tareaDao, productoDao, userDao)
    }

    fun insert(entity: Turno): Turno {
        return turnoDao.new(entity.id) {
            worker = userDao.findById(entity.worker.id) ?: throw Exception()
            maquina = maquinaDao.findById(entity.maquina.id) ?: throw Exception()
            horaInicio = entity.horaInicio
            horaFin = entity.horaFin
            numPedidosActivos = entity.numPedidosActivos
            tarea1 = entity.tarea1?.id?.let { tareaDao.findById(it) }
            tarea2 = entity.tarea2?.id?.let { tareaDao.findById(it) }
        }.fromTurnoDaoToTurno(tareaDao, productoDao, userDao)
    }

    override suspend fun delete(entity: Turno): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val existe = turnoDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}