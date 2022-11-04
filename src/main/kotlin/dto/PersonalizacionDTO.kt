package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Producto
import java.util.UUID

class PersonalizacionDTO() {
    lateinit var id: UUID
    lateinit var raqueta: Producto
    var peso: Double = 0.0
    var balance: Double = 0.0
    var rigidez: Double = 0.0
    val precio: Double = 60.0

    constructor(
        id: UUID?,
        raqueta: Producto,
        peso: Double,
        balance: Double,
        rigidez: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.peso = peso
        this.balance = balance
        this.rigidez = rigidez
    }

    fun fromJSON(json: String): PersonalizacionDTO? {
        return Gson().fromJson(json, PersonalizacionDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}