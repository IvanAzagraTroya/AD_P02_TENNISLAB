package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Producto
import java.util.*

class EncordadoDTO() {
    lateinit var id: UUID
    lateinit var raqueta: Producto
    var tensionHorizontal: Double = 0.0
    lateinit var cordajeHorizontal: Producto
    var tensionVertical: Double = 0.0
    lateinit var cordajeVertical: Producto
    var dosNudos: Boolean = true
    val precio: Double = (15.0+cordajeHorizontal.precio+cordajeVertical.precio)

    constructor(
        id: UUID?,
        raqueta: Producto,
        tensionHorizontal: Double,
        cordajeHorizontal: Producto,
        tensionVertical: Double,
        cordajeVertical: Producto,
        dosNudos: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.tensionHorizontal = tensionHorizontal
        this.cordajeHorizontal = cordajeHorizontal
        this.tensionVertical = tensionVertical
        this.cordajeVertical = cordajeVertical
        this.dosNudos = dosNudos
    }

    fun fromJSON(json: String): EncordadoDTO? {
        return Gson().fromJson(json, EncordadoDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}