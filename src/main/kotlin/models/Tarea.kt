package models

import models.enums.TipoTarea
import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase abstracta de la entidad tarea con un identificador
 * y el producto que se pasará
 */
// TODO revisar el funcionamiento de esta clase
open class Tarea(){
    open lateinit var id: UUID
    lateinit var raqueta: Producto
    open var precio: Double = 0.0
    lateinit var user: User
    lateinit var tipoTarea: TipoTarea

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
