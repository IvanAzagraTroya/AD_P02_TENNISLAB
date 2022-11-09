package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.enums.TipoProducto
import java.util.UUID

class ProductoDTO() {
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
    ) : this () {
        this.id = id ?: UUID.randomUUID()
        this.tipoProducto = tipoProducto
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.stock = stock ?: 0
    }

    fun fromJSON(json: String): ProductoDTO? {
        return Gson().fromJson(json, ProductoDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}