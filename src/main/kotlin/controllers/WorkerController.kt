package controllers

import com.google.gson.GsonBuilder
import dto.WorkerDTO
import java.sql.SQLException
import java.util.*

object WorkerController {
    /*
    private val service = WorkerService()

    @Throws(SQLException::class)
    fun findAllWorkers(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllWorkers())
            ?: throw SQLException("Error at WorkerController.findAllWorkers")
    }

    @Throws(SQLException::class)
    private fun getWorkerById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getWorkerById(UUID.fromString(id)))
            ?: throw SQLException("Worker with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getWorkerById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getWorkerById(id))
            ?: throw SQLException("Worker with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertWorker(dto: WorkerDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createWorker(dto))
            ?: throw SQLException("Could not insert Worker with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updateWorker(dto: WorkerDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updateWorker(dto))
            ?: throw SQLException("Could not update Worker with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deleteWorker(dto: WorkerDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteWorker(dto))
            ?: throw SQLException("Could not delete Worker with id ${dto.id}")
    }

     */
}