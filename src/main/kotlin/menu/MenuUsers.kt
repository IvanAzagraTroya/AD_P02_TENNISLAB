package menu

import controllers.UserController
import dto.UserDTO
import models.enums.Profile
import util.betweenXandY
import java.util.*

fun menuUsers() {
    var back = false
    while (!back) {
        println(" - Please select one of the following actions.")
        println("""
            1. Find all users
            2. Find all users with a particular role
            3. Find user with a particular id
            4. Find user with a particular email
            5. Find user with a particular phone number
            6. Create user
            7. Update user
            8. Delete user
            9. Back
        """.trimIndent())
        var res = ""
        while (!betweenXandY(res, 1, 9)) {
            res = readln()
        }
        when (res.toInt()) {
            1 -> println(UserController.findAllUsers())
            2 -> findAllByRole()
            3 -> findId()
            4 -> findEmail()
            5 -> findPhone()
            6 -> create()
            7 -> update()
            8 -> delete()
            9 -> back = true
        }
    }
}

private fun delete() {
    println(" - Email of target user:")
    val email = readln()
    val baseUser = UserController.getUserByEmailForLogin(email)
    if (baseUser == null) println("There are no users with email: $email")
    else {
        println("Deleting ${baseUser.nombre} ${baseUser.apellido} will be a permanent action. " +
                "Do you really want to proceed? [y/n]")
        var input = ""
        while (!input.contentEquals("y") && !input.contentEquals("n"))
            input = readln()
        if (input.contentEquals("y")) {
            println("Deleting user...")
            println(UserController.deleteUser(baseUser))
        }
    }
}

private fun update() {
    println(" - Current email of target user:")
    val email = readln()
    val baseUser = UserController.getUserByEmailForLogin(email)
    if (baseUser == null) println("There are no users with email: $email")
    else {
        println(" - Input new data:")
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
        lateinit var profile: Profile
        var input = ""
        println(" - Profile: [admin/worker/client]")
        while (!input.contentEquals("admin") &&
            !input.contentEquals("worker") &&
            !input.contentEquals("client")
        )
            input = readln()
        when (input) {
            "admin" -> profile = Profile.ADMIN
            "worker" -> profile = Profile.WORKER
            "client" -> profile = Profile.CLIENT
        }
        val newUser = UserDTO(
            id = baseUser.id,
            nombre = name,
            apellido = famName,
            telefono = number,
            email = mail,
            password = pwd,
            perfil = profile
        )
        println(UserController.insertUser(newUser))
    }
}

private fun create() {
    var goBack = false
    while (!goBack) {
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
        lateinit var profile: Profile
        var input = ""
        println(" - Profile: [admin/worker/client]")
        while (!input.contentEquals("admin") &&
            !input.contentEquals("worker") &&
            !input.contentEquals("client"))
            input = readln()
        when (input) {
            "admin" -> profile = Profile.ADMIN
            "worker" -> profile = Profile.WORKER
            "client" -> profile = Profile.CLIENT
        }
        val user1 = UserController.getUserByEmailForLogin(mail)
        val user2 = UserController.getUserByPhoneForLogin(number)
        if (user1 == null && user2 == null) {
            val newUser = UserDTO(
                id = UUID.randomUUID(),
                nombre = name,
                apellido = famName,
                telefono = number,
                email = mail,
                password = pwd,
                perfil = profile
            )
            println(UserController.insertUser(newUser))
            goBack = true
        }
        else {
            println(" - This email or phone number is already registered. Do you want to go back? [y/n]")
            var res = ""
            while (!res.contentEquals("y") && !res.contentEquals("n")) res = readln()
            if (res.contentEquals("y")) goBack = true
        }
    }
}

private fun findPhone() {
    println(" - Input phone:")
    val input = readln()
    println(UserController.getUserByPhone(input))
}

private fun findEmail() {
    println(" - Input email:")
    val input = readln()
    println(UserController.getUserByEmail(input))
}

private fun findId() {
    println(" - Input id:")
    var id: UUID? = null
    var correctFormat = false
    while (!correctFormat) {
        val input = readln()
        id = UUID.fromString(input) ?: null
        if (id != null) correctFormat = true
    }
    println(UserController.getUserById(id!!))
}

private fun findAllByRole() {
    var input = ""
    println(" - Select a role: [admin/worker/client]")
    while (!input.contentEquals("admin") &&
        !input.contentEquals("worker") &&
        !input.contentEquals("client"))
        input = readln()
    when (input) {
        "admin" -> println(UserController.findAllUsersWithRole(Profile.ADMIN))
        "worker" -> println(UserController.findAllUsersWithRole(Profile.WORKER))
        "client" -> println(UserController.findAllUsersWithRole(Profile.CLIENT))
    }
}
