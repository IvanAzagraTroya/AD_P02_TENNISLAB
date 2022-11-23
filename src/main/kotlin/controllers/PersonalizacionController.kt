package controllers

import com.google.gson.GsonBuilder
import dto.PersonalizacionDTO
import services.PersonalizacionService
import util.generateRespuesta
import java.util.*

object PersonalizacionController {
    private val service = PersonalizacionService()

    suspend fun findAllPersonalizaciones(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizaciones())
            ?: "Error at PersonalizacionController.findAllPersonalizaciones"
        return "prueba"//generateRespuesta(result, "Error at PersonalizacionController.findAllPersonalizaciones")
    }

    suspend fun getPersonalizacionById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPersonalizacionById(id))
            ?: "Personalizacion with id $id not found."
        return "prueba"//generateRespuesta(result, "Personalizacion with id $id not found.")
    }

    suspend fun insertPersonalizacion(dto: PersonalizacionDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPersonalizacion(dto))
            ?: "Could not insert Personalizacion with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Personalizacion with id ${dto.id}")
    }

    suspend fun deletePersonalizacion(dto: PersonalizacionDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePersonalizacion(dto))
            ?: "Could not delete Personalizacion with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Personalizacion with id ${dto.id}")
    }
}