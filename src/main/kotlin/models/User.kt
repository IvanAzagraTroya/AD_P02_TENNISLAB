package models

import com.google.gson.GsonBuilder
import java.util.UUID

class User() {
    lateinit var id:UUID
    lateinit var nombre: String
    lateinit var apellido: String
    lateinit var telefono: String
    lateinit var email: String
    lateinit var password: String
    lateinit var perfil: String

    constructor(
        id: UUID?,
        nombre: String,
        apellido: String,
        telefono: String,
        email: String,
        password: String,
        perfil: String
    ) : this(){
        this.id = id ?: UUID.randomUUID()
        this.nombre = nombre
        this.apellido = apellido
        this.telefono = telefono
        this.email = email
        this.password = password
        this.perfil = perfil
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .create().toJson(this)
    }
}