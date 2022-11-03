package models

import java.time.LocalDate
import java.util.*

data class Pedido(
    val id: UUID = UUID.randomUUID(),
    val tareas: List<Tarea>,
    val client: User,
    val state: Estado,
    val maquina: Maquina,
    val fechaEntrada: LocalDate,
    val fechaProgramada: LocalDate,
    val fechaSalida: LocalDate,
    var fechaEntrega: LocalDate = fechaSalida, // Pone que en principio tendrán que ser la misma fecha y que más adelante se actualizará a la que termine siendo
    val precio: Double
)


enum class Estado(){
    RECIBIDO,
    PROCESO,
    TERMINADO
}
