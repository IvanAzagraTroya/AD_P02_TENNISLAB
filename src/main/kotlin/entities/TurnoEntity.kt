package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Tabla de Turno.
 */
object TurnoTable: UUIDTable("TURNOS") {
    val worker = reference("worker_id", UserTable)
    val maquina = reference("maquina_id", MaquinaTable)
    val horaInicio = datetime("hora_inicio")
    val horaFin = datetime("hora_fin")
    val numPedidosActivos = integer("numero_pedidos_activos")
    val tarea1 = reference("tarea1_id", TareaTable)
    val tarea2 = reference("tarea2_id", TareaTable).nullable()
}

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Dao de Turno.
 */
class TurnoDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<TurnoDao>(TurnoTable)
    var worker by UserDao referencedOn TurnoTable.worker
    var maquina by MaquinaDao referencedOn TurnoTable.maquina
    var horaInicio by TurnoTable.horaInicio
    var horaFin by TurnoTable.horaFin
    var numPedidosActivos by TurnoTable.numPedidosActivos
    var tarea1 by TareaDao referencedOn TurnoTable.tarea1
    var tarea2 by TareaDao optionalReferencedOn TurnoTable.tarea2
}