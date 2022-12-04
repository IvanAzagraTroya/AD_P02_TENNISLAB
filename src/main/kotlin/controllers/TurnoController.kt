package controllers

import com.google.gson.GsonBuilder
import dto.TurnoDTO
import dto.UserDTO
import services.TurnoService
import util.generateRespuesta
import java.util.*

object TurnoController {
    var service = TurnoService()

    suspend fun findAllTurnos(): String {
        return service.getAllTurnos().toString()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllTurnos())
            ?: "Error at TurnoController.findAllTurnos"
        return "prueba"//generateRespuesta(result, "Error at TurnoController.findAllTurnos")

         */
    }

    suspend fun findAllTurnosSortedByFecha(): String {
        return service.getAllTurnos().sortedBy { it.horaInicio }.toString()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllTurnos().sortedBy { it.horaInicio })
            ?: "Error at TurnoController.findAllTurnosSortedByFecha"
        return "prueba"//generateRespuesta(result, "Error at TurnoController.findAllTurnosSortedByFecha")

         */
    }

    suspend fun getTurnoById(id: UUID): String {
        return service.getTurnoById(id)?.toJSON() ?: "Turno with id $id not found."
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getTurnoById(id))
            ?: "Turno with id $id not found."
        return "prueba"//generateRespuesta(result, "Turno with id $id not found.")

         */
    }

    suspend fun insertTurno(dto: TurnoDTO): String {
        return service.createTurno(dto).toJSON()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createTurno(dto))
            ?: "Could not insert Turno with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Turno with id ${dto.id}")

         */
    }

    suspend fun deleteTurno(dto: TurnoDTO): String {
        val result = service.deleteTurno(dto)
        return if (result) dto.toJSON()
        else "Could not delete Turno with id ${dto.id}"
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteTurno(dto))
            ?: "Could not delete Turno with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Turno with id ${dto.id}")

         */
    }
}