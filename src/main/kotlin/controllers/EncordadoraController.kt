package controllers

import com.google.gson.GsonBuilder
import dto.EncordadoraDTO
import java.sql.SQLException
import java.util.*

object EncordadoraController {
    /*
    private val service = EncordadoraService()

    @Throws(SQLException::class)
    fun findAllEncordadoras(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllEncordadoras())
            ?: throw SQLException("Error at EncordadoraController.findAllEncordadoras")
    }

    @Throws(SQLException::class)
    private fun getEncordadoraById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getEncordadoraById(UUID.fromString(id)))
            ?: throw SQLException("Encordadora with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getEncordadoraById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getEncordadoraById(id))
            ?: throw SQLException("Encordadora with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertEncordadora(dto: EncordadoraDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createEncordadora(dto))
            ?: throw SQLException("Could not insert Encordadora with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updateEncordadora(dto: EncordadoraDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updateEncordadora(dto))
            ?: throw SQLException("Could not update Encordadora with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deleteEncordadora(dto: EncordadoraDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteEncordadora(dto))
            ?: throw SQLException("Could not delete Encordadora with id ${dto.id}")
    }
    */
}