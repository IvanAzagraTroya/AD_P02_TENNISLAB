package controllers

import com.google.gson.GsonBuilder
import dto.UserDTO
import java.sql.SQLException
import java.util.*

object UserController {
    /*
    private val service = UserService()

    @Throws(SQLException::class)
    fun findAllUsers(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllUsers())
            ?: throw SQLException("Error at UserController.findAllUsers")
    }

    @Throws(SQLException::class)
    private fun getUserById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserById(UUID.fromString(id)))
            ?: throw SQLException("User with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getUserById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserById(id))
            ?: throw SQLException("User with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertUser(dto: UserDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createUser(dto))
            ?: throw SQLException("Could not insert User with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updateUser(dto: UserDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updateUser(dto))
            ?: throw SQLException("Could not update User with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deleteUser(dto: UserDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteUser(dto))
            ?: throw SQLException("Could not delete User with id ${dto.id}")
    }

     */
}