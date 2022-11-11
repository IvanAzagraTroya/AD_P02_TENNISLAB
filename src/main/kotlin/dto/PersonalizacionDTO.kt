package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Producto
import models.User
import util.toLocalMoney
import java.util.*

class PersonalizacionDTO() {
    lateinit var id: UUID
    lateinit var raqueta: Producto
    lateinit var user: User
    var peso: Double = 0.0
    var balance: Double = 0.0
    var rigidez: Double = 0.0
    val precio: Double = 60.0

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        peso: Double,
        balance: Double,
        rigidez: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
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

    override fun toString(): String {
        return "Tarea(id=$id, " +
                "tipo=Personalizacion, " +
                "raqueta=$raqueta, " +
                "user=$user, " +
                "peso=$peso g, " +
                "balance=$balance, " +
                "rigidez=$rigidez, " +
                "precio=${precio.toLocalMoney(Locale("es", "ES"))})"
    }
}