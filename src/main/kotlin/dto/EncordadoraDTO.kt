package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.util.UUID

class EncordadoraDTO() {
    lateinit var id: UUID
    lateinit var modelo: String
    lateinit var marca: String
    lateinit var fechaAdquisicion: LocalDate
    lateinit var numeroSerie: String
    var isManual: Boolean = true
    var maxTension: Double = 0.0
    var minTension: Double = 0.0

    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion: LocalDate?,
        numeroSerie: String,
        isManual: Boolean,
        maxTension: Double,
        minTension: Double
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.isManual = isManual
        this.maxTension = maxTension
        this.minTension = minTension
    }

    fun fromJSON(json: String): EncordadoraDTO? {
        return Gson().fromJson(json, EncordadoraDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}