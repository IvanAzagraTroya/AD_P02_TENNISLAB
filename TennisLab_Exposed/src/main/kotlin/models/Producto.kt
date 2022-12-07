package models

import models.enums.TipoProducto
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Producto, que será
 * traducida a un Dao.
 */
class Producto() {
    lateinit var id: UUID
    lateinit var tipoProducto: TipoProducto
    lateinit var marca: String
    lateinit var modelo: String
    var precio: Double = 0.0
    var stock: Int = 0

    constructor(
        id: UUID?,
        tipoProducto: TipoProducto,
        marca: String,
        modelo: String,
        precio: Double,
        stock: Int?
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.tipoProducto = tipoProducto
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.stock = stock ?: 0
    }
}
