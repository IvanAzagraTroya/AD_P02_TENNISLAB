package models

import java.time.LocalDateTime
import java.util.*

class Turno() {
    lateinit var id: UUID
    lateinit var worker: User
    lateinit var maquina: Maquina
    lateinit var horaInicio: LocalDateTime
    lateinit var horaFin: LocalDateTime
    var numPedidosActivos: Int = 0
    var tarea1: Tarea? = null
    var tarea2: Tarea? = null

    constructor(
        id: UUID?,
        worker: User,
        maquina: Maquina,
        horaInicio: LocalDateTime,
        tarea1: Tarea?,
        tarea2: Tarea?
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.worker = worker
        this.maquina = maquina
        this.horaInicio = horaInicio
        // Asi los turnos duran 4 horas siempre.
        this.horaFin = this.horaInicio.plusHours(4L)
        this.tarea1 = tarea1
        this.tarea2 = tarea2

        if (this.tarea1 != null) {
            numPedidosActivos++
        }
        if (this.tarea2 != null) {
            numPedidosActivos++
        }
    }
}