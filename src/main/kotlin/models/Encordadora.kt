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
