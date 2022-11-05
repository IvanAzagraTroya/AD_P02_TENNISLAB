package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Producto
import java.util.UUID

class AdquisicionDTO() {
    lateinit var id: UUID
    lateinit var raqueta: Producto
    lateinit var productoAdquirido: Producto
    var precio: Double = 0.0

    constructor(
        id: UUID?,
        raqueta: Producto,
        productoAdquirido: Producto,
        precio: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.productoAdquirido = productoAdquirido
        this.precio = precio
    }

    fun fromJSON(json: String): AdquisicionDTO? {
        return Gson().fromJson(json, AdquisicionDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}