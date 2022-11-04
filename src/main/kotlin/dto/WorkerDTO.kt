package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import models.Maquina
import models.Profile
import java.util.*

class WorkerDTO() {
    lateinit var id: UUID
    lateinit var nombre: String
    lateinit var apellido: String
    lateinit var telefono: String
    lateinit var email: String
    lateinit var password: String
    val perfil: Profile = Profile.WORKER
    lateinit var maquina: Maquina
    var horaInicioTurno: Int = 0
    var horaFinTurno: Int = 0
    var numPedidosActivos: Int = 0

    constructor(
        id: UUID?,
        nombre: String,
        apellido: String,
        telefono: String,
        email: String,
        password: String,
        maquina: Maquina,
        horaInicioTurno: Int,
        horaFinTurno: Int,
        numPedidosActivos: Int?
    ) :this() {
        this.id = id ?: UUID.randomUUID()
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.email = email
        this.password = password
        this.maquina = maquina
        this.horaInicioTurno = horaInicioTurno
        this.horaFinTurno = horaFinTurno
        this.numPedidosActivos = numPedidosActivos ?: 0
    }

    fun fromJSON(json: String): WorkerDTO? {
        return Gson().fromJson(json, WorkerDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}