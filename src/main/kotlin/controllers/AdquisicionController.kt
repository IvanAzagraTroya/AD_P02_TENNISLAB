package controllers

import dto.AdquisicionDTO
import services.AdquisicionService
import java.util.*

object AdquisicionController {
    private val service = AdquisicionService()

    suspend fun findAllAdquisiciones(): String {
        return service.getAllAdquisiciones().toString()
    //return "prueba"//generateRespuesta(service.getAllAdquisiciones())
    }

    suspend fun getAdquisicionById(id: UUID): String {
        return service.getAdquisicionById(id)?.toJSON() ?: "Adquisicion with id $id not found."
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAdquisicionById(id))
            ?: "Adquisicion with id $id not found."
        return "prueba"//generateRespuesta(result, "Adquisicion with id $id not found.")

         */
    }

    suspend fun insertAdquisicion(dto: AdquisicionDTO): String {
        return service.createAdquisicion(dto).toJSON()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createAdquisicion(dto))
            ?: "Could not insert Adquisicion with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Adquisicion with id ${dto.id}")

         */
    }

    suspend fun deleteAdquisicion(dto: AdquisicionDTO): String {
        val result = service.deleteAdquisicion(dto)
        return if (result) dto.toJSON()
        else "Could not delete Adquisicion with id ${dto.id}"
        /*
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteAdquisicion(dto))
            ?: "Could not delete Adquisicion with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Adquisicion with id ${dto.id}")

         */
    }
}