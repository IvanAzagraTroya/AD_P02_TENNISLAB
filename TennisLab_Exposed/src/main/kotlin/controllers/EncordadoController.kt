package controllers

import dto.EncordadoDTO
import services.EncordadoService
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Controlador de Encordado. Emplea el servicio para realizar todas las operaciones
 * que necesitemos de esta entidad, devolviendo los resultados como JSON.
 */
object EncordadoController {
    var service = EncordadoService()

    suspend fun findAllEncordados(): String {
        return service.getAllEncordados().toString()
    }

    suspend fun getEncordadoById(id: UUID): String {
        return service.getEncordadoById(id)?.toJSON() ?: "Encordado with id $id not found."
    }

    suspend fun insertEncordado(dto: EncordadoDTO): String {
        return service.createEncordado(dto).toJSON()
    }

    suspend fun deleteEncordado(dto: EncordadoDTO): String {
        val result = service.deleteEncordado(dto)
        return if (result) dto.toJSON()
        else "Could not delete Encordado with id ${dto.id}"
    }
}