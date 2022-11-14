package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object EncordadoTable: UUIDTable("ENCORDADOS") {
    val tensionHorizontal = double("tension_horizontal")
    val tensionVertical = double("tension_vertical")
    val cordajeHorizontal = reference("producto_id", ProductoTable)
    val cordajeVertical = reference("producto_id", ProductoTable)
    val dosNudos = bool("dos_nudos")
    val precio = double("precio")
}

class EncordadoDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<EncordadoDao>(EncordadoTable)

    var tensionHorizontal by EncordadoTable.tensionHorizontal
    var tensionVertical by EncordadoTable.tensionVertical
    val cordajeHorizontal = ProductoDao referencedOn EncordadoTable.cordajeHorizontal
    val cordajeVertical = ProductoDao referencedOn EncordadoTable.cordajeVertical
    var dosNudos by EncordadoTable.dosNudos
    var precio by EncordadoTable.precio
}