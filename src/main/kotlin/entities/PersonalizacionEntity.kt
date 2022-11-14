package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object PersonalizacionTable: UUIDTable("Personalizaciones") {
    val peso = integer("peso")
    val balance = double("balance")
    val rigidez = integer("rigidez")
}

class PersonalizacionDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<PersonalizadoraDao>(PersonalizacionTable)
    var peso by PersonalizacionTable.peso
    var balance by PersonalizacionTable.balance
    var rigidez by PersonalizacionTable.rigidez
}