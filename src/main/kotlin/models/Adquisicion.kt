package models

import java.util.*

data class Adquisicion(
    override var id: UUID,
    val productoAdquirido: Producto,
    val precio: Double
): Tarea(id)
