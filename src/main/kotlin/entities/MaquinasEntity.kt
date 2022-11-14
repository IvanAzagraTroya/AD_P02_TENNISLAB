package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

object MaquinasTable: UUIDTable("MAQUINAS") {
    val modelo = varchar("modelo", 255)
    val marca = varchar("marca", 255)
    val fechaAdquisicion = date("fecha_adquisicion")
    val numeroSerie = varchar("numero_serie", 255)
}

class MaquinasDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<MaquinasDao>(MaquinasTable)

    var modelo by MaquinasTable.modelo
    var marca by MaquinasTable.marca
    var fechaAdquisicion by MaquinasTable.fechaAdquisicion
    var numeroSerie by MaquinasTable.numeroSerie
}
