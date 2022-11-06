package controllers

import com.google.gson.GsonBuilder
import dto.EncordadoDTO
import java.sql.SQLException
import java.util.*

object EncordadoController {
    /*
    private val service = EncordadoService()

    @Throws(SQLException::class)
    fun findAllEncordados(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllEncordados())
            ?: throw SQLException("Error at EncordadoController.findAllEncordados")
    }

    @Throws(SQLException::class)
    private fun getEncordadoById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getEncordadoById(UUID.fromString(id)))
            ?: throw SQLException("Encordado with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getEncordadoById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getEncordadoById(id))
            ?: throw SQLException("Encrodado with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertEncordado(dto: EncordadoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createEncordado(dto))
            ?: throw SQLException("Could not insert Encordado with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updateEncordado(dto: EncordadoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updateEncordado(dto))
            ?: throw SQLException("Could not update Encordado with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deleteEncordado(dto: EncordadoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteEncordado(dto))
            ?: throw SQLException("Could not delete Encordado with id ${dto.id}")
    }
    */
}