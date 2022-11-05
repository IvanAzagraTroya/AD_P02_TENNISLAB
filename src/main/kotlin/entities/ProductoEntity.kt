package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ProductoTable: UUIDTable() {
    val tipoProducto = varchar("tipo_producto", 255)
    val marca = varchar("marca", 255)
    val modelo = varchar("modelo", 255)
    val precio = double("precio")
    val stock = integer("stock")
}

class ProductoDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<ProductoDao>(ProductoTable)

    var tipoProducto by ProductoTable.tipoProducto
    var marca by ProductoTable.marca
    var modelo by ProductoTable.modelo
    var precio by ProductoTable.precio
    var stock by ProductoTable.stock
}