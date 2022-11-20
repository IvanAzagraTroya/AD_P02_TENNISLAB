package controllers

import com.google.gson.GsonBuilder
import dto.PersonalizadoraDTO
import services.PersonalizadoraService
import util.generateRespuesta
import java.util.*

object PersonalizadoraController {
    private val service = PersonalizadoraService()

    fun findAllPersonalizadoras(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizadoras())
            ?: "Error at PersonalizadoraController.findAllPersonalizadoras"
        return generateRespuesta(result, "Error at PersonalizadoraController.findAllPersonalizadoras")
    }

    fun getPersonalizadoraById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPersonalizadoraById(id))
            ?: "Personalizadora with id $id not found."
        return generateRespuesta(result, "Personalizadora with id $id not found.")
    }

    fun insertPersonalizadora(dto: PersonalizadoraDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPersonalizadora(dto))
            ?: "Could not insert Personalizadora with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Personalizadora with id ${dto.id}")
    }

    fun deletePersonalizadora(dto: PersonalizadoraDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePersonalizadora(dto))
            ?: "Could not delete Personalizadora with id ${dto.id}"
        return generateRespuesta(result, "Could not delete Personalizadora with id ${dto.id}")
    }
}