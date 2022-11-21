package controllers

import com.google.gson.GsonBuilder
import dto.EncordadoraDTO
import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import services.EncordadoraService
import services.PersonalizadoraService
import util.generateRespuesta
import java.time.LocalDate
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

    fun getMaquinaBySerialNumber(sNum: String): String {
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.numeroSerie.contentEquals(sNum) }
        val encordadoras = eService.getAllEncordadoras().filter { it.numeroSerie.contentEquals(sNum) }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(maquinas.toList().firstOrNull())
            ?: "Maquina with serial number $sNum not found."
        return generateRespuesta(result, "Maquina with serial number $sNum not found.")
    }

    fun getMaquinaBySerialNumberForCreation(sNum: String): MaquinaDTO? {
        val encordadoras = eService.getAllEncordadoras().filter { it.numeroSerie == sNum }
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.numeroSerie == sNum }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        return maquinas.toList().firstOrNull()
    }

    fun getMaquinaByModel(model: String): String {
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.modelo.contentEquals(model) }
        val encordadoras = eService.getAllEncordadoras().filter { it.modelo.contentEquals(model) }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(maquinas.toList())
            ?: "Maquina with model $model not found."
        return generateRespuesta(result, "Maquina with model $model not found.")
    }

    fun getMaquinaByBrand(brand: String): String {
        val personalizadoras = pService.getAllPersonalizadoras().filter { it.marca.contentEquals(brand) }
        val encordadoras = eService.getAllEncordadoras().filter { it.marca.contentEquals(brand) }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(maquinas.toList())
            ?: "Maquina with marca $brand not found."
        return generateRespuesta(result, "Maquina with marca $brand not found.")
    }

    fun findAllMaquinasByAcquisitionDate(date: LocalDate, operador: String): String {
        lateinit var personalizadoras: List<PersonalizadoraDTO>
        lateinit var encordadoras: List<EncordadoraDTO>
        when (operador) {
            ">" -> {
                personalizadoras = pService.getAllPersonalizadoras().filter { it.fechaAdquisicion > date }
                encordadoras = eService.getAllEncordadoras().filter { it.fechaAdquisicion > date }
            }
            "<" -> {
                personalizadoras = pService.getAllPersonalizadoras().filter { it.fechaAdquisicion < date }
                encordadoras = eService.getAllEncordadoras().filter { it.fechaAdquisicion < date }
            }
            else -> {
                personalizadoras = listOf()
                encordadoras = listOf()
            }
        }
        val maquinas: MutableList<MaquinaDTO> = mutableListOf()
        personalizadoras.forEach { maquinas.add(it) }
        encordadoras.forEach { maquinas.add(it) }
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(maquinas.toList())
            ?: "Error at MaquinaController.findAllMaquinasByAquisitionDate"
        return generateRespuesta(result, "Error at MaquinaController.findAllMaquinasByAquisitionDate")
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