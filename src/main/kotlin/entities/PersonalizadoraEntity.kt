package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object PersonalizadoraTable: UUIDTable("Personalizadora") {
    val measuresManeuverability = bool("Maniobrabilidad")
    val measuresBalance = bool("Balance")
    val measuresRigidity = bool("Rigidez")
}

class PersonalizadoraDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<PersonalizadoraDao>(PersonalizadoraTable)

    var measuresManeuverability: Boolean by PersonalizadoraTable.measuresManeuverability
    var measuresBalance: Boolean by PersonalizadoraTable.measuresBalance
    var measuresRigidity: Boolean by PersonalizadoraTable.measuresRigidity
}