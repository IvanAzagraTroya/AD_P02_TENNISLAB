package models

import models.PersonalizadoraDao.Companion.referrersOn
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase POKO de la entidad worker
 */

data class Worker(
    override var id: UUID,
    val maquina: Maquina,
    val horaInicio: Int,
    val horaFin: Int,
    var numPedidos: Int = 0 // relación 0..2, por defecto hay 0 pedidos
    ) :User(id)

object WorkerTable: UUIDTable("Workers") {
    val maquina = reference("id_maquina", MaquinasTable, onDelete = ReferenceOption.SET_NULL) //Si no está realizando ninguna tarea no debería tener ninguna máquina asignada
    val horaInicio = integer("hora_inicio")
    val horaFin = integer("hora_fin")
    val numPedidos = integer("num_pedidos").default(0)
}

class WorkerDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<WorkerDao>(WorkerTable)

    var maquina by MaquinasDao referencedOn WorkerTable.maquina // todo Esto puede que deba cambiarse
    var horaInicio by WorkerTable.horaInicio
    var horaFin by WorkerTable.horaFin
    var numPedidos by WorkerTable.numPedidos
}
