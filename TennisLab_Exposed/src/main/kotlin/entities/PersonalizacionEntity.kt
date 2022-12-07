package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Tabla de Perosnalizacion.
 */
object PersonalizacionTable: UUIDTable("PERSONALIZACIONES") {
    val peso = integer("peso")
    val balance = double("balance")
    val rigidez = integer("rigidez")
}

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Dao de Personalizacion.
 */
class PersonalizacionDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<PersonalizacionDao>(PersonalizacionTable)
    var peso by PersonalizacionTable.peso
    var balance by PersonalizacionTable.balance
    var rigidez by PersonalizacionTable.rigidez
}