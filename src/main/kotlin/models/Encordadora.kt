package models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.*

data class Encordadora(
    override var id: UUID,
    val isManual: Boolean,
    val maxTension: Double,
    val minTension: Double
    ):Maquina(id)

object EncordadoraTable: UUIDTable("Encordadora") {
    val isManual= bool("is_manual")
    val maxTension = double("max_tension")
    val minTension = double("min_tension")
}

class EncordadoraDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<EncordadoraDao>(EncordadoraTable)
    var isManual by EncordadoraTable.isManual
    var maxTension by EncordadoraTable.maxTension
    var minTension by EncordadoraTable.minTension
}