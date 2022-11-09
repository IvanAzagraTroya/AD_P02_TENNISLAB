package controllers

import com.google.gson.GsonBuilder
import dto.AdquisicionDTO
import java.sql.SQLException
import java.util.*

object AdquisicionController {
    //private val service = AdquisicionService()
    /*
    @Throws(SQLException::class)
    fun findAllAdquisiciones(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllAdquisiciones())
            ?: throw SQLException("Error at AdquisicionController.findAllAdquisiciones")
    }

    @Throws(SQLException::class)
    private fun getAdquisicionById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAdquisicionById(UUID.fromString(id)))
            ?: throw SQLException("Adquisicion with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getAdquisicionById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAdquisicionById(id))
            ?: throw SQLException("Adquisicion with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertAdquisicion(dto: AdquisicionDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createAdquisicion(dto))
            ?: throw SQLException("Could not insert Adquisicion with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updateAdquisicion(dto: AdquisicionDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updateAdquisicion(dto))
            ?: throw SQLException("Could not update Adquisicion with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deleteAdquisicion(dto: AdquisicionDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteAdquisicion(dto))
            ?: throw SQLException("Could not delete Adquisicion with id ${dto.id}")
    }

     */
}