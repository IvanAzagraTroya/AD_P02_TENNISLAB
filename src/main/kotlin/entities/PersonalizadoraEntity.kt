package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object PersonalizadoraTable: UUIDTable("PERSONALIZADORAS") {
    val measuresManeuverability = bool("mide_maniobrabilidad")
    val measuresBalance = bool("mide_balance")
    val measuresRigidity = bool("mide_rigidez")
}

class PersonalizadoraDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<PersonalizadoraDao>(PersonalizadoraTable)
    var measuresManeuverability: Boolean by PersonalizadoraTable.measuresManeuverability
    var measuresBalance: Boolean by PersonalizadoraTable.measuresBalance
    var measuresRigidity: Boolean by PersonalizadoraTable.measuresRigidity
}