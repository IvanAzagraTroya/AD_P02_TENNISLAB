package models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

open class User(
    open var id:UUID = UUID.randomUUID()
) {
    lateinit var nombre: String
    lateinit var apellido: String
    lateinit var telefono: String
    lateinit var email: String
    lateinit var contraseña: String

    constructor(id:UUID, nombre: String, apellido: String,
                telefono: String, email: String, contraseña: String) : this(){
        this.id = id
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.email = email
        this.contraseña = contraseña
    }
}

object UserTable: UUIDTable("Users") {
    val nombre = varchar("nombre", 255)
    val apellido = varchar("apellido", 255)
    val telefono = varchar("telefono", 255)
    val email = varchar("email", 255)
    val contraseña = varchar("contrasenia", 255) //todo codificación sha512
}

class UsersDao(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<UsersDao>(UserTable)
    var nombre: String by UserTable.nombre
    var apellido: String by UserTable.apellido
    var telefono: String by UserTable.telefono
    var email: String by UserTable.email
    var contraseña: String by UserTable.contraseña
}