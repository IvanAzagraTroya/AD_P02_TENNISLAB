package controllers

import com.google.gson.GsonBuilder
import dto.EncordadoraDTO
import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import services.EncordadoraService
import services.PersonalizadoraService
import util.generateRespuesta
import java.util.*

object MaquinaController {
    val pService = PersonalizadoraService()
    val eService = EncordadoraService()

    fun findAllMaquinas(): String {
        val personalizadoras = pService.getAllPersonalizadoras()
        val encordadoras = eService.getAllEncordadoras()
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(maquinas.toList())
            ?: "Error at MaquinaController.findAllMaquinas"
        return generateRespuesta(result, "Error at MaquinaController.findAllMaquinas")
    }

    fun getMaquinaById(id: UUID): String {
        var busqueda: MaquinaDTO? = pService.getPersonalizadoraById(id)
        return if (busqueda != null) {
            PersonalizadoraController.getPersonalizadoraById(id)
        } else {
            busqueda = eService.getEncordadoraById(id)
            if (busqueda != null) {
                EncordadoraController.getEncordadoraById(id)
            } else {
                generateRespuesta(
                    "Maquina with id $id not found.",
                    "Maquina with id $id not found."
                )
            }
        }
    }

    fun insertMaquina(dto: MaquinaDTO): String {
        return when (dto) {
            is PersonalizadoraDTO -> PersonalizadoraController.insertPersonalizadora(dto)
            is EncordadoraDTO -> EncordadoraController.insertEncordadora(dto)
            else -> { generateRespuesta(
                "Error at MaquinaController.insertMaquina: DTO not supported.",
                "Error at MaquinaController.insertMaquina: DTO not supported."
            ) }
        }
    }

    fun deleteMaquina(dto: MaquinaDTO): String {
        return when (dto) {
            is PersonalizadoraDTO -> PersonalizadoraController.deletePersonalizadora(dto)
            is EncordadoraDTO -> EncordadoraController.deleteEncordadora(dto)
            else -> { generateRespuesta(
                    "Error at MaquinaController.deleteMaquina: DTO not supported.",
                    "Error at MaquinaController.deleteMaquina: DTO not supported."
            ) }
        }
    }
}