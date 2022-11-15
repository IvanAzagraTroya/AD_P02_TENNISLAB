package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object AdquisicionTable: UUIDTable("ADQUISICIONES") {
    val productoAdquirido = reference("producto_adquirido_id", ProductoTable)
    val precio = double("precio")
}

class AdquisicionDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<AdquisicionDao>(AdquisicionTable)

    var productoAdquirido by ProductoDao referencedOn AdquisicionTable.productoAdquirido
    var precio by AdquisicionTable.precio
}