package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Tabla de User.
 */
object UserTable: UUIDTable("USERS") {
    val nombre = varchar("nombre", 255)
    val apellido = varchar("apellido", 255)
    val telefono = varchar("telefono", 255).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val perfil = varchar("perfil", 255)
}

/**
 * @author Ivan Azagra Troya
 *
 * Dao de User.
 */
class UserDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDao>(UserTable)
    var nombre: String by UserTable.nombre
    var apellido: String by UserTable.apellido
    var telefono: String by UserTable.telefono
    var email: String by UserTable.email
    var password: String by UserTable.password
    var perfil: String by UserTable.perfil
}