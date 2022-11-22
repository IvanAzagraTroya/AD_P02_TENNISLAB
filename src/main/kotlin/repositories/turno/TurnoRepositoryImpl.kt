package repositories.turno

import entities.*
import mappers.fromTurnoDaoToTurno
import models.Turno
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TurnoRepositoryImpl(
    private val turnoDao: UUIDEntityClass<TurnoDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>
): ITurnoRepository {
    override fun readAll(): List<Turno> = transaction {
        turnoDao.all().map { it.fromTurnoDaoToTurno(tareaDao, productoDao, userDao) }
    }

    override fun findById(id: UUID): Turno? = transaction {
        turnoDao.findById(id)?.fromTurnoDaoToTurno(tareaDao, productoDao, userDao)
    }

    override fun create(entity: Turno): Turno {
        val existe = turnoDao.findById(entity.id)
        return existe?.let { update(entity, existe) }
            ?: run { insert(entity) }
    }

    private fun update(entity: Turno, existe: TurnoDao): Turno = transaction {
        existe.apply {
            worker = userDao.findById(entity.worker.id) ?: throw Exception()
            maquina = maquinaDao.findById(entity.maquina.id) ?: throw Exception()
            horaInicio = entity.horaInicio
            horaFin = entity.horaFin
            numPedidosActivos = entity.numPedidosActivos
            tarea1 = entity.tarea1?.id?.let { tareaDao.findById(it) }
            tarea2 = entity.tarea2?.id?.let { tareaDao.findById(it) }
        }.fromTurnoDaoToTurno(tareaDao, productoDao, userDao)
    }

    fun insert(entity: Turno): Turno = transaction {
        turnoDao.new(entity.id) {
            worker = userDao.findById(entity.worker.id) ?: throw Exception()
            maquina = maquinaDao.findById(entity.maquina.id) ?: throw Exception()
            horaInicio = entity.horaInicio
            horaFin = entity.horaFin
            numPedidosActivos = entity.numPedidosActivos
            tarea1 = entity.tarea1?.id?.let { tareaDao.findById(it) }
            tarea2 = entity.tarea2?.id?.let { tareaDao.findById(it) }
        }.fromTurnoDaoToTurno(tareaDao, productoDao, userDao)
    }

    override fun delete(entity: Turno): Boolean = transaction {
        val existe = turnoDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}