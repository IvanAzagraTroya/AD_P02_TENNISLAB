package controllers

import com.google.gson.GsonBuilder
import dto.EncordadoraDTO
import services.EncordadoraService
import util.generateRespuesta
import java.util.*

object EncordadoraController {
    private val service = EncordadoraService()

    fun findAllEncordadoras(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllEncordadoras())
            ?: "Error at EncordadoraController.findAllEncordadoras"
        return generateRespuesta(result, "Error at EncordadoraController.findAllEncordadoras")
    }

    fun getEncordadoraById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getEncordadoraById(id))
            ?: "Encordadora with id $id not found."
        return generateRespuesta(result, "Encordadora with id $id not found.")
    }

    fun insertEncordadora(dto: EncordadoraDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createEncordadora(dto))
            ?: "Could not insert Encordadora with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Encordadora with id ${dto.id}")
    }

    fun deleteEncordadora(dto: EncordadoraDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteEncordadora(dto))
            ?: "Could not delete Encordadora with id ${dto.id}"
        return generateRespuesta(result, "Could not delete Encordadora with id ${dto.id}")
    }
}