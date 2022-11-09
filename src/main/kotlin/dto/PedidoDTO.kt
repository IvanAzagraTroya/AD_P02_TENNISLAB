package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Maquina
import models.Tarea
import models.User
import models.enums.PedidoEstado
import java.time.LocalDate
import java.util.UUID

class PedidoDTO() {
    lateinit var id: UUID
    lateinit var tareas: List<Tarea>
    lateinit var client: User
    lateinit var worker: User
    lateinit var state: PedidoEstado
    lateinit var maquina: Maquina
    lateinit var fechaEntrada: LocalDate
    lateinit var fechaProgramada: LocalDate
    lateinit var fechaSalida: LocalDate
    lateinit var fechaEntrega: LocalDate
    var precio: Double = 0.0

    constructor(
        id: UUID?,
        tareas: List<Tarea>?,
        client: User,
        worker: User,
        state: PedidoEstado,
        maquina: Maquina,
        fechaEntrada: LocalDate?,
        fechaProgramada: LocalDate,
        fechaSalida: LocalDate,
        fechaEntrega: LocalDate?,
        precio: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.tareas = tareas ?: listOf()
        this.client = client
        this.worker = worker
        this.state = state
        this.maquina = maquina
        this.fechaEntrada = fechaEntrada ?: LocalDate.now()
        this.fechaProgramada = fechaProgramada
        this.fechaSalida = fechaSalida
        this.fechaEntrega = fechaEntrega ?: fechaSalida
        this.precio = precio
    }

    fun fromJSON(json: String): PedidoDTO? {
        return Gson().fromJson(json, PedidoDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}