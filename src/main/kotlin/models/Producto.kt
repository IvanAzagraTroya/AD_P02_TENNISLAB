package models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase POKO de la entidad producto
 */
data class Producto(
    val id: UUID,
    val tipoProducto: TipoProducto,
    val marca: String,
    val modelo: String,
    val precio: Double,
    val stock: Int
    )

/**
 * @author Iván Azagra Troya
 * Enumerador de los tipos de productos que puede haber junto a un
 * companion object que actúa como parser
 */
enum class TipoProducto(value: String){
    RAQUETAS("RAQUETAS"),
    CORDAJES("CORDAJES"),
    OVERGRIPS("OVERGRIPS"),
    GRIPS("GRIPS"),
    ANTIVIBRADORES("ANTIVIBRADORES"),
    FUNDAS("FUNDAS");

    companion object {
        fun parseTipoProducto(value: String): TipoProducto {
            return when (value) {
                "RAQUETAS" -> RAQUETAS
                "CORDAJES" -> CORDAJES
                "OVERGRIPS" -> OVERGRIPS
                "GRIPS" -> GRIPS
                "ANTIVIBRADORES" -> ANTIVIBRADORES
                "FUNDAS" -> FUNDAS
                else -> throw IllegalArgumentException("Ese tipo de producto no existe")
            }
        }
    }
}

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

