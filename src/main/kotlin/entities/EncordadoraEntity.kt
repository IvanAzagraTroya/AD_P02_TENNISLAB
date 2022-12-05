package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Tabla de Encordadora.
 */
object EncordadoraTable: UUIDTable("ENCORDADORAS") {
    val isManual= bool("is_manual")
    val maxTension = double("max_tension")
    val minTension = double("min_tension")
}

/**
 * @author Ivan Azagra Troya
 *
 * Dao de Encordadora.
 */
class EncordadoraDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<EncordadoraDao>(EncordadoraTable)
    var isManual by EncordadoraTable.isManual
    var maxTension by EncordadoraTable.maxTension
    var minTension by EncordadoraTable.minTension
}