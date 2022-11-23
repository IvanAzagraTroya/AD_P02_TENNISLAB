package controllers

import com.google.gson.GsonBuilder
import dto.AdquisicionDTO
import services.AdquisicionService
import util.generateRespuesta
import java.util.*

object AdquisicionController {
    private val service = AdquisicionService()

    suspend fun findAllAdquisiciones(): String {
        return "prueba"//generateRespuesta(service.getAllAdquisiciones())
    }

    suspend fun getAdquisicionById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAdquisicionById(id))
            ?: "Adquisicion with id $id not found."
        return "prueba"//generateRespuesta(result, "Adquisicion with id $id not found.")
    }

    suspend fun insertAdquisicion(dto: AdquisicionDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createAdquisicion(dto))
            ?: "Could not insert Adquisicion with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Adquisicion with id ${dto.id}")
    }

    suspend fun deleteAdquisicion(dto: AdquisicionDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteAdquisicion(dto))
            ?: "Could not delete Adquisicion with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Adquisicion with id ${dto.id}")
    }
}