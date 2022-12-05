package models

import models.enums.TipoMaquina
import java.time.LocalDate
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase POKO de Maquina, que será
 * traducida a un Dao.
 */
open class Maquina() {
    open lateinit var id: UUID
    lateinit var modelo: String
    lateinit var marca: String
    lateinit var fechaAdquisicion: LocalDate
    lateinit var numeroSerie: String
    lateinit var tipoMaquina: TipoMaquina

    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion:LocalDate?,
        numeroSerie: String,
        tipoMaquina: TipoMaquina
    ) : this(){
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.tipoMaquina = tipoMaquina
    }
}
