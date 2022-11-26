package controllers

import com.google.gson.GsonBuilder
import dto.UserDTO
import models.enums.Profile
import services.UserService
import util.generateRespuesta
import java.util.*

object UserController {
    var service = UserService()

    suspend fun findAllUsers(): String {
        //return generateRespuesta(service.getAllUsers())
        return service.getAllUsers().toString()
    }

    suspend fun findAllUsersWithRole(role: Profile): String {
        return service.getAllUsers().filter { it.perfil == role }.toString()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllUsers().filter { it.perfil == role })
            ?: "Error at UserController.findAllUsersWithRole $role"
        return generateRespuesta(result, "Error at UserController.findAllUsersWithRole $role")

         */

        //return generateRespuesta(service.getAllUsers().filter { it.perfil == role })
    }

    suspend fun getUserById(id: UUID): String {
        return service.getUserById(id)?.toJSON() ?: "User with id $id not found."
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserById(id))
            ?: "User with id $id not found."
        return generateRespuesta(result, "User with id $id not found.")

         */

        //return generateRespuesta(service.getUserById(id))
    }

    suspend fun getUserByEmail(email: String): String {
        return service.getUserByMail(email)?.toJSON() ?: "User with email $email not found."
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserByMail(email))
            ?: "User with email $email not found."
        return generateRespuesta(result, "User with email $email not found.")

         */

        //return generateRespuesta(service.getUserByMail(email))
    }

    suspend fun getUserByEmailForLogin(email: String): UserDTO? {
        return service.getUserByMail(email)
    }

    suspend fun getUserByPhone(phone: String): String {
        return service.getUserByPhone(phone)?.toJSON() ?: "User with phone $phone not found."
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getUserByPhone(phone))
            ?: "User with phone $phone not found."
        return generateRespuesta(result, "User with phone $phone not found.")

         */

        //return generateRespuesta(service.getUserByPhone(phone))
    }

    suspend fun getUserByPhoneForLogin(phone: String): UserDTO? {
        return service.getUserByPhone(phone)
    }

    suspend fun insertUser(dto: UserDTO): String {
        return service.createUser(dto).toJSON()
    }

    suspend fun deleteUser(dto: UserDTO): String {
        val result = service.deleteUser(dto)
        return if (result) dto.toJSON()
        else "Could not delete User with id ${dto.id}"
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteUser(dto))
            ?: "Could not delete User with id ${dto.id}"
        return generateRespuesta(result, "Could not delete User with id ${dto.id}")

         */

        //return generateRespuesta(service.deleteUser(dto))
    }
}