package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.Maquina
import models.Pedido
import models.Tarea
import models.User
import java.time.LocalDateTime
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase POKO de Turno usada en el programa.
 * Consta de un metodo toString() overrideado que
 * devuelve el objeto como JSON, asi como un metodo
 * fromJSON y un metodo toJSON.
 */
class TurnoDTO() {
    @Expose lateinit var id: UUID
    @Expose lateinit var worker: User
    @Expose lateinit var maquina: Maquina
    lateinit var horaInicio: LocalDateTime
    lateinit var horaFin: LocalDateTime
    @Expose lateinit var horaInicioString: String
    @Expose lateinit var horaFinString: String
    @Expose var numPedidosActivos: Int = 1
    @Expose lateinit var tarea1: Tarea
    @Expose var tarea2: Tarea? = null
    @Expose lateinit var pedido: Pedido

    constructor(
        id: UUID?,
        worker: User,
        maquina: Maquina,
        horaInicio: LocalDateTime,
        horaFin: LocalDateTime?,
        tarea1: Tarea,
        tarea2: Tarea?,
        pedido: Pedido
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.worker = worker
        this.maquina = maquina
        this.horaInicio = horaInicio
        this.horaFin = horaFin ?: this.horaInicio.plusHours(4L)
        this.tarea1 = tarea1
        this.tarea2 = tarea2
        this.horaInicioString = horaInicio.toString()
        this.horaFinString = horaFin.toString()
        this.pedido = pedido

        if (this.tarea2 != null) {
            numPedidosActivos++
        }
    }

    fun fromJSON(json: String): TurnoDTO? {
        return Gson().fromJson(json, TurnoDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}