package controllers

import com.google.gson.GsonBuilder
import dto.TurnoDTO
import dto.UserDTO
import services.TurnoService
import util.generateRespuesta
import java.util.*

object TurnoController {
    private val service = TurnoService()

    fun findAllTurnos(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllTurnos())
            ?: "Error at TurnoController.findAllTurnos"
        return generateRespuesta(result, "Error at TurnoController.findAllTurnos")
    }

    fun findAllTurnosSortedByFecha(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllTurnos().sortedBy { it.horaInicio })
            ?: "Error at TurnoController.findAllTurnosSortedByFecha"
        return generateRespuesta(result, "Error at TurnoController.findAllTurnosSortedByFecha")
    }

    fun getTurnoById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getTurnoById(id))
            ?: "Turno with id $id not found."
        return generateRespuesta(result, "Turno with id $id not found.")
    }

    fun insertTurno(dto: TurnoDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createTurno(dto))
            ?: "Could not insert Turno with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Turno with id ${dto.id}")
    }

    fun deleteTurno(dto: TurnoDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteTurno(dto))
            ?: "Could not delete Turno with id ${dto.id}"
        return generateRespuesta(result, "Could not delete Turno with id ${dto.id}")
    }
}