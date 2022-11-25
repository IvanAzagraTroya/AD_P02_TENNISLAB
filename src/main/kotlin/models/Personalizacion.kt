package models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.enums.TipoTarea
import java.util.*

class Personalizacion(): Tarea() {
    var peso: Int = 0
    var balance: Double = 0.0
    var rigidez: Int = 0

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        peso: Int,
        balance: Double,
        rigidez: Int
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.peso = peso
        this.balance = balance
        this.rigidez = rigidez
        this.precio = 60.0
        this.tipoTarea = TipoTarea.PERSONALIZACION
    }

    constructor(
        id: UUID?,
        peso: Int,
        balance: Double,
        rigidez: Int
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.peso = peso
        this.balance = balance
        this.rigidez = rigidez
        this.precio = 60.0
        this.tipoTarea = TipoTarea.PERSONALIZACION
    }
}
