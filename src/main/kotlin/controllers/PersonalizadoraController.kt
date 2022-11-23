package controllers

import com.google.gson.GsonBuilder
import dto.PersonalizadoraDTO
import services.PersonalizadoraService
import util.generateRespuesta
import java.util.*

object PersonalizadoraController {
    private val service = PersonalizadoraService()

    suspend fun findAllPersonalizadoras(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizadoras())
            ?: "Error at PersonalizadoraController.findAllPersonalizadoras"
        return "prueba"//generateRespuesta(result, "Error at PersonalizadoraController.findAllPersonalizadoras")
    }

    suspend fun findAllManeuverability(bool: Boolean): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizadoras().filter { it.measuresManeuverability == bool })
            ?: "Error at PersonalizadoraController.findAllManeuverability"
        return "prueba"//generateRespuesta(result, "Error at PersonalizadoraController.findAllManeuverability")
    }

    suspend fun findAllRigidity(bool: Boolean): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizadoras().filter { it.measuresRigidity == bool })
            ?: "Error at PersonalizadoraController.findAllRigidity"
        return "prueba"//generateRespuesta(result, "Error at PersonalizadoraController.findAllRigidity")
    }

    suspend fun findAllBalance(bool: Boolean): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPersonalizadoras().filter { it.measuresBalance == bool })
            ?: "Error at PersonalizadoraController.findAllBalance"
        return "prueba"//generateRespuesta(result, "Error at PersonalizadoraController.findAllBalance")
    }

    suspend fun getPersonalizadoraById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPersonalizadoraById(id))
            ?: "Personalizadora with id $id not found."
        return "prueba"//generateRespuesta(result, "Personalizadora with id $id not found.")
    }

    suspend fun insertPersonalizadora(dto: PersonalizadoraDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPersonalizadora(dto))
            ?: "Could not insert Personalizadora with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Personalizadora with id ${dto.id}")
    }

    suspend fun deletePersonalizadora(dto: PersonalizadoraDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePersonalizadora(dto))
            ?: "Could not delete Personalizadora with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Personalizadora with id ${dto.id}")
    }
}