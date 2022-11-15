package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Producto
import models.User
import util.toLocalMoney
import java.util.*

class AdquisicionDTO(): TareaDTO {
    lateinit var id: UUID
    lateinit var raqueta: Producto
    lateinit var user: User
    lateinit var productoAdquirido: Producto
    var precio: Double = productoAdquirido.precio

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        productoAdquirido: Producto
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.productoAdquirido = productoAdquirido
    }

    fun fromJSON(json: String): AdquisicionDTO? {
        return Gson().fromJson(json, AdquisicionDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

    override fun toString(): String {
        return "Tarea(id=$id, " +
                "tipo=Adquisicion, " +
                "raqueta=$raqueta, " +
                "user=$user, " +
                "productoAdquirido=$productoAdquirido, " +
                "precio=${precio.toLocalMoney(Locale("es", "ES"))})"
    }
}