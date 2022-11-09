package models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.*

data class Encordadora(
    override var id: UUID = UUID.randomUUID(),
    ):Maquina(id) {
    var isManual: Boolean = false
    var maxTension: Double = 0.0
    var minTension: Double = 0.0
        constructor(isManual: Boolean, maxTension: Double, minTension: Double
        ): this() {
            this.isManual = isManual
            this.maxTension = maxTension
            this.minTension = minTension
        }
    }
