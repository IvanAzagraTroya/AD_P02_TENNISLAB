package controllers

import com.google.gson.GsonBuilder
import dto.EncordadoraDTO
import services.EncordadoraService
import util.generateRespuesta
import java.util.*

object EncordadoraController {
    private val service = EncordadoraService()

    suspend fun findAllEncordadoras(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllEncordadoras())
            ?: "Error at EncordadoraController.findAllEncordadoras"
        return "prueba"//generateRespuesta(result, "Error at EncordadoraController.findAllEncordadoras")
    }

    suspend fun findAllManuales(bool: Boolean): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllEncordadoras().filter { it.isManual == bool })
            ?: "Error at EncordadoraController.findAllManuales"
        return "prueba"//generateRespuesta(result, "Error at EncordadoraController.findAllManuales")
    }

    suspend fun getEncordadoraById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getEncordadoraById(id))
            ?: "Encordadora with id $id not found."
        return "prueba"//generateRespuesta(result, "Encordadora with id $id not found.")
    }

    suspend fun insertEncordadora(dto: EncordadoraDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createEncordadora(dto))
            ?: "Could not insert Encordadora with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Encordadora with id ${dto.id}")
    }

    suspend fun deleteEncordadora(dto: EncordadoraDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteEncordadora(dto))
            ?: "Could not delete Encordadora with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Encordadora with id ${dto.id}")
    }
}