package models

import com.google.gson.GsonBuilder
import models.enums.TipoTarea
import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase abstracta de la entidad tarea con un identificador
 * y el producto que se pasará
 */
open class Tarea(){
    open lateinit var id: UUID
    lateinit var raqueta: Producto
    open var precio: Double = 0.0
    lateinit var user: User
    lateinit var tipoTarea: TipoTarea
    lateinit var pedido: Pedido

    constructor(
        id: UUID?,
        raqueta: Producto,
        precio: Double?,
        user: User,
        tipoTarea: TipoTarea
    ) : this () {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.precio = precio ?: 0.0
        this.user = user
        this.tipoTarea = tipoTarea
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .create().toJson(this)
    }
}
