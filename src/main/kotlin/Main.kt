import controllers.*
import db.*
import kotlinx.coroutines.*
import login.login
import login.register
import menu.menu
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) = runBlocking {
//    Por algún motivo solo deja llamar al Database desde main
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    val dataLoader = DataLoader()

    println("Cargando datos iniciales...")
    val job1 = launch(Dispatchers.IO) {
        dataLoader.getUsers().forEach { println(UserController.insertUserInit(it)) }
        println("Users loaded.")
    }
    val job2 = launch(Dispatchers.IO) {
        dataLoader.getMaquinas().forEach { println(MaquinaController.insertMaquinaInit(it)) }
        println("Maquinas loaded.")
    }
    val job3 = launch(Dispatchers.IO) {
        dataLoader.getProductos().forEach { println(ProductoController.insertProductoInit(it)) }
        println("Productos loaded.")
    }
    lateinit var job4: Job

    while (!job1.isCompleted || !job2.isCompleted ||
            !job3.isCompleted || !job4.isCompleted) {
        for (i in 1..3) {
            print(".")
            delay(500)
        }
        println()
    }

    job1.join()
    job2.join()
    job3.join()

    job4 = launch(Dispatchers.IO) {
        dataLoader.getTareas().forEach { println(TareaController.insertTareaInit(it)) }
        println("Tareas loaded.")
        dataLoader.getTurnos().forEach { println(TurnoController.insertTurnoInit(it)) }
        println("Turnos loaded.")
        dataLoader.getPedidos().forEach { println(PedidoController.insertPedidoInit(it)) }
        println("Pedidos loaded.")
    }

    job4.join()

    println("Data successfully loaded.")

    var loginEnter = ""
    println(" - Welcome. Do you want to log in or register? [login/register]")
    while (!loginEnter.contentEquals("login") && ! loginEnter.contentEquals("register")) {
        loginEnter = readln()
    }
    val user = if (loginEnter.contentEquals("login")) login() else register()

    menu(user)
}
