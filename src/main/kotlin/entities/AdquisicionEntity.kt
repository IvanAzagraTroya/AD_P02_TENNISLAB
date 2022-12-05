package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Tabla de Adquisicion.
 */
object AdquisicionTable: UUIDTable("ADQUISICIONES") {
    val productoAdquirido = reference("producto_adquirido_id", ProductoTable)
    val precio = double("precio")
}

/**
 * @author Ivan Azagra Troya
 *
 * Dao de Adquisicion.
 */
class AdquisicionDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<AdquisicionDao>(AdquisicionTable)

    var productoAdquirido by ProductoDao referencedOn AdquisicionTable.productoAdquirido
    var precio by AdquisicionTable.precio
}