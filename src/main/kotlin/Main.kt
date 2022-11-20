import login.login
import login.register
import menu.menu
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
//    Por algún motivo solo deja llamar al Database desde main
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    var loginEnter = ""
    println(" - Welcome. Do you want to log in or register? [login/register]")
    while (!loginEnter.contentEquals("login") && ! loginEnter.contentEquals("register")) {
        loginEnter = readln()
    }
    val user = if (loginEnter.contentEquals("login")) login() else register()

    menu(user)
}
