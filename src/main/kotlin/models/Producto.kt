package models

import models.enums.TipoProducto
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase POKO de la entidad producto
 */
class Producto(
    var id: UUID = UUID.randomUUID()
    ) {
    lateinit var tipoProducto: TipoProducto
    lateinit var marca: String
    lateinit var modelo: String
    var precio: Double = 0.0
    var stock: Int = 0
    constructor(tipoProducto: TipoProducto, marca: String, modelo: String, precio: Double, stock: Int
    ): this() {
        this.id = UUID.randomUUID()
        this.tipoProducto = tipoProducto
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.stock = stock
    }
}
