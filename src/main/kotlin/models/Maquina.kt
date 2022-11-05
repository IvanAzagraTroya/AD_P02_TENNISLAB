package models

import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.time.LocalDate
import java.util.*

abstract class Maquina(
    open var id: UUID = UUID.randomUUID()
)
{
    lateinit var modelo: String
    lateinit var marca: String
    lateinit var fechaAdquisicion: LocalDate
    lateinit var numeroSerie: String

    constructor(modelo: String, marca: String, fechaAdquisicion:LocalDate, numeroSerie: String) : this(){
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion
        this.numeroSerie = numeroSerie
    }
}
