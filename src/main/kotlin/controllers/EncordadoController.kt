package controllers

import com.google.gson.GsonBuilder
import dto.EncordadoDTO
import services.EncordadoService
import util.generateRespuesta
import java.util.*

object EncordadoController {
    private val service = EncordadoService()

    suspend fun findAllEncordados(): String {
        return service.getAllEncordados().toString()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllEncordados())
            ?: "Error at EncordadoController.findAllEncordados"
        return "prueba"//generateRespuesta(result, "Error at EncordadoController.findAllEncordados")

         */
    }

    suspend fun getEncordadoById(id: UUID): String {
        return service.getEncordadoById(id)?.toJSON() ?: "Encordado with id $id not found."
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getEncordadoById(id))
            ?: "Encordado with id $id not found."
        return "prueba"//generateRespuesta(result, "Encordado with id $id not found.")

         */
    }

    suspend fun insertEncordado(dto: EncordadoDTO): String {
        return service.createEncordado(dto).toJSON()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createEncordado(dto))
            ?: "Could not insert Encordado with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Encordado with id ${dto.id}")

         */
    }

    suspend fun deleteEncordado(dto: EncordadoDTO): String {
        val result = service.deleteEncordado(dto)
        return if (result) dto.toJSON()
        else "Could not delete Encordado with id ${dto.id}"
        /*
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteEncordado(dto))
            ?: "Could not delete Encordado with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Encordado with id ${dto.id}")

         */
    }
}