package models

import java.util.*

data class Encordado(
    override var id: UUID,
    val tensionHorizontal: Double,
    val cordajeHorizontal: Producto,
    val tensionVertical: Double,
    val cordajeVertical: Producto,
    val dosNudos: Boolean,
    val precio: Double = 15.0
):Tarea(id)
