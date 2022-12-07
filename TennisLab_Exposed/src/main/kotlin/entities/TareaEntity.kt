package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Tabla de Tarea.
 */
object TareaTable: UUIDTable("TAREAS") {
    val raqueta = reference("raqueta_id", ProductoTable)
    val precio = double("precio")
    val user = reference("user_id", UserTable)
    val tipoTarea = varchar("tipo_tarea", 255)
}

/**
 * @author Ivan Azagra Troya
 *
 * Dao de Tarea.
 */
class TareaDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<TareaDao>(TareaTable)
    var raqueta by ProductoDao referencedOn TareaTable.raqueta
    var precio by TareaTable.precio
    var user by UserDao referencedOn TareaTable.user
    var tipoTarea by TareaTable.tipoTarea
}