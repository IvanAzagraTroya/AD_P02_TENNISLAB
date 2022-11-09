package models

import java.util.UUID

open class User(
    open var id:UUID = UUID.randomUUID()
) {
    lateinit var nombre: String
    lateinit var apellido: String
    lateinit var telefono: String
    lateinit var email: String
    lateinit var contraseña: String
    lateinit var  perfil: String

    constructor(nombre: String, apellido: String,
                telefono: String, email: String, contraseña: String, perfil: String
    ) : this(){
        this.id
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.email = email
        this.contraseña = contraseña
        this.perfil = perfil
    }
}