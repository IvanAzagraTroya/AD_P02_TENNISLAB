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
    private lateinit var modelo: String
    private lateinit var marca: String
    private lateinit var fechaAdquisicion: LocalDate
    private lateinit var numeroSerie: String

    constructor(modelo: String, marca: String, fechaAdquisicion:LocalDate, numeroSerie: String) : this(){
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion
        this.numeroSerie = numeroSerie
    }
}

object MaquinasTable: UUIDTable("Maquinas") {
    val modelo = varchar("modelo", 255)
    val marca = varchar("marca", 255)
    val fechaAdquisicion: String? = date("fecha_adquisicion")
    val numeroSerie = varchar("numero_serie", 255)
}

class MaquinasDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<MaquinasDao>(MaquinasTable)

    var modelo by MaquinasTable.modelo
    var marca by MaquinasTable.marca
//    var fechaAdquisicion by MaquinasTable.fechaAdquisicion todo da error porque pide un String para la fecha :)
    var numeroSerie by MaquinasTable.numeroSerie
}
