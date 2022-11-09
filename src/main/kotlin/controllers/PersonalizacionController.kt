package controllers

import com.google.gson.GsonBuilder
import dto.PersonalizacionDTO
import java.sql.SQLException
import java.util.*

object PersonalizacionController {
    /*
    private val service = PersonalizacionService()

    @Throws(SQLException::class)
    fun findAllPersonalizaciones(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizaciones())
            ?: throw SQLException("Error at PersonalizacionController.findAllPersonalizaciones")
    }

    @Throws(SQLException::class)
    private fun getPersonalizacionById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPersonalizacionById(UUID.fromString(id)))
            ?: throw SQLException("Personalizacion with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getPersonalizacionById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPersonalizacionById(id))
            ?: throw SQLException("Personalizacion with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertPersonalizacion(dto: PersonalizacionDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPersonalizacion(dto))
            ?: throw SQLException("Could not insert Personalizacion with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updatePersonalizacion(dto: PersonalizacionDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updatePersonalizacion(dto))
            ?: throw SQLException("Could not update Personalizacion with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deletePersonalizacion(dto: PersonalizacionDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePersonalizacion(dto))
            ?: throw SQLException("Could not delete Personalizacion with id ${dto.id}")
    }
    */
}