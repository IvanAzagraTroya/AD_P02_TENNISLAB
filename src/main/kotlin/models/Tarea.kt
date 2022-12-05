package models

import models.enums.TipoTarea
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase POKO de Tarea, que será
 * traducida a un Dao.
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
}
