package controllers

import com.google.gson.GsonBuilder
import dto.PersonalizadoraDTO
import java.sql.SQLException
import java.util.*

object PersonalizadoraController {
    /*
    private val service = PersonalizadoraService()

    @Throws(SQLException::class)
    fun findAllPersonalizadora(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizadora())
            ?: throw SQLException("Error at PersonalizadoraController.findAllPersonalizadoras")
    }

    @Throws(SQLException::class)
    private fun getPersonalizadoraById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPersonalizadoraById(UUID.fromString(id)))
            ?: throw SQLException("Personalizadora with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getPersonalizadoraById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPersonalizadoraById(id))
            ?: throw SQLException("Personalizadora with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertPersonalizadora(dto: PersonalizadoraDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPersonalizadora(dto))
            ?: throw SQLException("Could not insert Personalizadora with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updatePersonalizadora(dto: PersonalizadoraDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updatePersonalizadora(dto))
            ?: throw SQLException("Could not update Personalizadora with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deletePersonalizadora(dto: PersonalizadoraDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePersonalizadora(dto))
            ?: throw SQLException("Could not delete Personalizadora with id ${dto.id}")
    }
    */
}