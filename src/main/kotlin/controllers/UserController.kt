package controllers

import com.google.gson.GsonBuilder
import dto.UserDTO
import models.enums.Profile
import services.UserService
import util.generateRespuesta
import java.util.*

object UserController {
    private val service = UserService()

    fun findAllUsers(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllUsers())
            ?: "Error at UserController.findAllUsers"
        return generateRespuesta(result, "Error at UserController.findAllUsers")
    }

    fun findAllUsersWithRole(role: Profile): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllUsers().filter { it.perfil == role })
            ?: "Error at UserController.findAllUsersWithRole $role"
        return generateRespuesta(result, "Error at UserController.findAllUsersWithRole $role")
    }

    fun getUserById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserById(id))
            ?: "User with id $id not found."
        return generateRespuesta(result, "User with id $id not found.")
    }

    fun getUserByEmail(email: String): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserByMail(email))
            ?: "User with email $email not found."
        return generateRespuesta(result, "User with email $email not found.")
    }

    fun getUserByEmailForLogin(email: String): UserDTO? {
        return service.getUserByMail(email)
    }

    fun getUserByPhone(phone: String): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserByPhone(phone))
            ?: "User with phone $phone not found."
        return generateRespuesta(result, "User with phone $phone not found.")
    }

    fun getUserByPhoneForLogin(phone: String): UserDTO? {
        return service.getUserByPhone(phone)
    }

    fun insertUser(dto: UserDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createUser(dto))
            ?: "Could not insert User with id ${dto.id}"
        return generateRespuesta(result, "Could not insert User with id ${dto.id}")
    }

    fun deleteUser(dto: UserDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteUser(dto))
            ?: "Could not delete User with id ${dto.id}"
        return generateRespuesta(result, "Could not delete User with id ${dto.id}")
    }

    fun insertUserInit(dto: UserDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createUserInit(dto))
            ?: "Could not insert User with id ${dto.id}"
        return generateRespuesta(result, "Could not insert User with id ${dto.id}")
    }
}