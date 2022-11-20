package login

import controllers.UserController
import dto.UserDTO
import models.enums.Profile
import util.encode
import java.util.*
import kotlin.system.exitProcess

fun login(): UserDTO {
    while (true) {
        println(" --- Login --- ")
        println(" - Email: ")
        val email = readln()
        println(" - Password: ")
        val pwd = readln()
        val user = UserController.getUserByEmailForLogin(email)
        var res = ""
        if(user == null) {
            println(" - Incorrect credentials. Do you want to exit? [y/n]")
            while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
            if (res.contentEquals("y")) exitProcess(0)
        }
        else {
            val encryptedPwd = encode(pwd)
            if (!encryptedPwd.contentEquals(user.password)) {
                println(" - Incorrect credentials. Do you want to exit? [y/n]")
                while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
                if (res.contentEquals("y")) exitProcess(0)
            }
            else {
                println(" - Logged in. Welcome ${user.nombre}.")
                return user
            }
        }
    }
}

fun register(): UserDTO {
    while (true) {
        println(" --- Register --- ")
        println(" - Name: ")
        val name = readln()
        println(" - Family name: ")
        val famName = readln()
        println(" - Phone number: ")
        val number = readln()
        println(" - Email: ")
        val mail = readln()
        println(" - Password: ")
        val pwd = readln()
        println(" - Repeat password: ")
        val rpwd = readln()
        val user1 = UserController.getUserByEmailForLogin(mail)
        val user2 = UserController.getUserByPhoneForLogin(number)
        if (pwd.contentEquals(rpwd) && user1 == null && user2 == null) {
            val newUser = UserDTO(
                id = UUID.randomUUID(),
                nombre = name,
                apellido = famName,
                telefono = number,
                email = mail,
                password = pwd,
                perfil = Profile.CLIENT
            )
            val result = UserController.insertUser(newUser)
            println("""
                Registering $name $famName...
                -------------------------------------
                
                $result
                
                -------------------------------------
            """.trimIndent())
            return newUser
        }
        else {
            println(" - Incorrect parameters. Do you want to exit? [y/n]")
            var res = ""
            while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
            if (res.contentEquals("y")) exitProcess(0)
        }
    }
}