package models

import java.time.LocalDate
import java.util.*

class Encordadora():Maquina() {
    override var id = super.id
    var isManual: Boolean = false
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
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.isManual = isManual
        this.maxTension = maxTension
        this.minTension = minTension
    }

    constructor(
        id: UUID?,
        isManual: Boolean,
        maxTension: Double,
        minTension: Double
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.isManual = isManual
        this.maxTension = maxTension
        this.minTension = minTension
    }
}
