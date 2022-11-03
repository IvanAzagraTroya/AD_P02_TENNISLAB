package models

import java.util.*

data class Personalizacion(
    override var id: UUID,
    val peso: Double,
    val balance: Double,
    val rigidez: Double,
    val precio: Double = 60.0
): Tarea(id)
