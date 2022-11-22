package controllers

import com.google.gson.GsonBuilder
import dto.AdquisicionDTO
import services.AdquisicionService
import util.generateRespuesta
import java.util.*

object AdquisicionController {
    private val service = AdquisicionService()

    fun findAllAdquisiciones(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllAdquisiciones())
            ?: "Error at AdquisicionController.findAllAdquisiciones"
        return generateRespuesta(result, "Error at AdquisicionController.findAllAdquisiciones")
    }

    fun getAdquisicionById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAdquisicionById(id))
            ?: "Adquisicion with id $id not found."
        return generateRespuesta(result, "Adquisicion with id $id not found.")
    }

    fun insertAdquisicion(dto: AdquisicionDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createAdquisicion(dto))
            ?: "Could not insert Adquisicion with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Adquisicion with id ${dto.id}")
    }

    fun deleteAdquisicion(dto: AdquisicionDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteAdquisicion(dto))
            ?: "Could not delete Adquisicion with id ${dto.id}"
        return generateRespuesta(result, "Could not delete Adquisicion with id ${dto.id}")
    }


    fun insertAdquisicionInit(dto: AdquisicionDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createAdquisicionInit(dto))
            ?: "Could not insert Adquisicion with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Adquisicion with id ${dto.id}")
    }
}