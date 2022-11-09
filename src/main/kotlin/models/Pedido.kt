package models

import models.enums.PedidoEstado
import java.time.LocalDate
import java.util.*

data class Pedido(
    val id: UUID = UUID.randomUUID(),
) {
    lateinit var tareas: List<Tarea>
    lateinit var client: User
    lateinit var state: PedidoEstado
    lateinit var maquina: Maquina
    lateinit var fechaEntrada: LocalDate
    lateinit var fechaProgramada: LocalDate
    lateinit var fechaSalida: LocalDate
    var fechaEntrega: LocalDate = fechaSalida // todo Pone que en principio tendrán que ser la misma fecha y que más adelante se actualizará a la que termine siendo
    var precio: Double = 0.0

    constructor(tareas: List<Tarea>, client: User, state: PedidoEstado, maquina: Maquina, fechaEntrada: LocalDate, fechaProgramada: LocalDate, fechaSalida: LocalDate, fechaEntrega: LocalDate = fechaSalida, precio: Double
    ): this() {
        this.id
        this.tareas = tareas
        this.client = client
        this.state = state
        this.maquina = maquina
        this.fechaEntrada = fechaEntrada
        this.fechaProgramada = fechaProgramada
        this.fechaSalida = fechaSalida
        this.fechaEntrega = fechaEntrega
        this.precio = precio
    }
}
