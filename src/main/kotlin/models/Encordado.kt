package models

import models.enums.TipoTarea
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Encordado, que será
 * traducida a un Dao.
 */
class Encordado():Tarea() {
    var tensionHorizontal: Double = 0.0
    lateinit var cordajeHorizontal: Producto
    var tensionVertical: Double = 0.0
    lateinit var cordajeVertical: Producto
    var dosNudos: Boolean = false

    constructor(
        id: UUID?,
        raqueta: Producto,
        user: User,
        tensionHorizontal: Double,
        cordajeHorizontal: Producto,
        tensionVertical: Double,
        cordajeVertical: Producto,
        dosNudos: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.user = user
        this.tensionHorizontal = tensionHorizontal
        this.tensionVertical = tensionVertical
        this.cordajeHorizontal = cordajeHorizontal
        this.cordajeVertical = cordajeVertical
        this.dosNudos = dosNudos
        this.precio = (15.0+cordajeHorizontal.precio+cordajeVertical.precio)
        this.tipoTarea = TipoTarea.ENCORDADO
    }
}
