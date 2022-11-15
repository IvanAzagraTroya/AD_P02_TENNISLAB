package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

object MaquinaTable: UUIDTable("MAQUINAS") {
    val modelo = varchar("modelo", 255)
    val marca = varchar("marca", 255)
    val fechaAdquisicion = date("fecha_adquisicion")
    val numeroSerie = varchar("numero_serie", 255)
    val tipoMaquina = varchar("tipo_maquina", 255)
}

class MaquinaDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<MaquinaDao>(MaquinaTable)

    var modelo by MaquinaTable.modelo
    var marca by MaquinaTable.marca
    var fechaAdquisicion by MaquinaTable.fechaAdquisicion
    var numeroSerie by MaquinaTable.numeroSerie
    var tipoMaquina by MaquinaTable.tipoMaquina
}
