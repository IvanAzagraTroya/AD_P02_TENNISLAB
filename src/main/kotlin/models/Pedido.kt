package models

import models.enums.PedidoEstado
import java.time.LocalDate
import java.util.*

class Pedido() {
    lateinit var id: UUID
    lateinit var tareas: List<Tarea>
    lateinit var client: User
    lateinit var turnos: List<Turno>
    lateinit var state: PedidoEstado
    lateinit var fechaEntrada: LocalDate
    lateinit var fechaProgramada: LocalDate
    lateinit var fechaSalida: LocalDate
    lateinit var fechaEntrega: LocalDate
    var precio: Double = 0.0

    constructor(
        id: UUID?,
        tareas: List<Tarea>, // todo SizedIterable
        client: User,
        turnos: List<Turno>, // todo SizedIterable
        state: PedidoEstado,
        fechaEntrada: LocalDate?,
        fechaProgramada: LocalDate,
        fechaSalida: LocalDate,
        fechaEntrega: LocalDate?
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.tareas = tareas
        this.client = client
        this.turnos = turnos
        this.state = state
        this.fechaEntrada = fechaEntrada ?: LocalDate.now()
        this.fechaProgramada = fechaProgramada
        this.fechaSalida = fechaSalida
        this.fechaEntrega = fechaEntrega ?: fechaSalida
        this.precio = tareas.sumOf { it.precio }
    }
}
