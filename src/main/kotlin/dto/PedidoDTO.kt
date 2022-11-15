package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Maquina
import models.Tarea
import models.Turno
import models.User
import models.enums.PedidoEstado
import util.toLocalDate
import util.toLocalMoney
import java.time.LocalDate
import java.util.*

class PedidoDTO() {
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
        tareas: List<Tarea>?,
        client: User,
        turnos: List<Turno>?,
        state: PedidoEstado,
        fechaEntrada: LocalDate?,
        fechaProgramada: LocalDate,
        fechaSalida: LocalDate,
        fechaEntrega: LocalDate?
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.tareas = tareas ?: listOf()
        this.client = client
        this.turnos = turnos ?: listOf()
        this.state = state
        this.fechaEntrada = fechaEntrada ?: LocalDate.now()
        this.fechaProgramada = fechaProgramada
        this.fechaSalida = fechaSalida
        this.fechaEntrega = fechaEntrega ?: fechaSalida
        this.precio = this.tareas.sumOf { it.precio }
    }

    fun fromJSON(json: String): PedidoDTO? {
        return Gson().fromJson(json, PedidoDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

    override fun toString(): String {
        return "Pedido(id=$id, " +
                "tareas=$tareas, " +
                "client=${client.nombre}, " +
                "turnos=$turnos, " +
                "state=$state, " +
                "fechaEntrada=${fechaEntrada.toLocalDate(Locale("es", "ES"))}, " +
                "fechaProgramada=${fechaProgramada.toLocalDate(Locale("es", "ES"))}, " +
                "fechaSalida=${fechaSalida.toLocalDate(Locale("es", "ES"))}, " +
                "fechaEntrega=${fechaEntrega.toLocalDate(Locale("es", "ES"))}, " +
                "precio=${precio.toLocalMoney(Locale("es", "ES"))})"
    }
}