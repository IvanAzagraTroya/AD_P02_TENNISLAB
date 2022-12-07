package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

/**
 * @author Ivan Azagra Troya
 *
 * Tabla de Encordado.
 */
object EncordadoTable: UUIDTable("ENCORDADOS") {
    val tensionHorizontal = double("tension_horizontal")
    val tensionVertical = double("tension_vertical")
    val cordajeHorizontal = reference("cordaje_horizontal_id", ProductoTable)
    val cordajeVertical = reference("cordaje_vertical_id", ProductoTable)
    val dosNudos = bool("dos_nudos")
    val precio = double("precio")
}

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Dao de Encordado.
 */
class EncordadoDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<EncordadoDao>(EncordadoTable)

    var tensionHorizontal by EncordadoTable.tensionHorizontal
    var tensionVertical by EncordadoTable.tensionVertical
    var cordajeHorizontal by ProductoDao referencedOn EncordadoTable.cordajeHorizontal
    var cordajeVertical by ProductoDao referencedOn EncordadoTable.cordajeVertical
    var dosNudos by EncordadoTable.dosNudos
    var precio by EncordadoTable.precio
}