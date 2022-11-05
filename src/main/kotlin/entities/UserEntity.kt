package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object UserTable: UUIDTable("Users") {
    val nombre = varchar("nombre", 255)
    val apellido = varchar("apellido", 255)
    val telefono = varchar("telefono", 255)
    val email = varchar("email", 255)
    val contraseña = varchar("contrasenia", 255) //todo codificación sha512
    val perfil = varchar("perfil", 255)
}

class UsersDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<UsersDao>(UserTable)
    var nombre: String by UserTable.nombre
    var apellido: String by UserTable.apellido
    var telefono: String by UserTable.telefono
    var email: String by UserTable.email
    var contraseña: String by UserTable.contraseña
    var perfil by UserTable.perfil
}