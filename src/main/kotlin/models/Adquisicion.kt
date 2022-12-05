package models

import com.google.gson.annotations.Expose
import models.enums.TipoTarea
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase POKO de Adquisicion, que será
 * traducida a un Dao.
 */
class Adquisicion(): Tarea() {
    @Expose
    lateinit var productoAdquirido: Producto

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        productoAdquirido: Producto,
        precio: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.productoAdquirido = productoAdquirido
        this.precio = precio
        this.tipoTarea = TipoTarea.ADQUISICION
    }

    constructor(
        id: UUID?,
        productoAdquirido: Producto,
        precio: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.productoAdquirido = productoAdquirido
        this.precio = precio
        this.tipoTarea = TipoTarea.ADQUISICION
    }
}
