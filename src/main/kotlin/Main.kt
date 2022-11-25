import config.AppConfig
import controllers.*
import db.*
import kotlinx.coroutines.*
import login.login
import login.register
import menu.menu
import org.jetbrains.exposed.sql.Database
import java.io.File
import java.util.UUID

fun main(args: Array<String>) {
    // esto está puesto así en vez de como
    // fun main(args: Array<String) = runBlocking { ... }
    // porque si no, no detectaba el main, ni idea de por qué,
    // literalmente hace 5 minutos lo detectaba sin problemas.
    runBlocking {
        initDB()

        val dataLoader = DataLoader()

        println("Cargando datos iniciales...")
        val job1 = launch(Dispatchers.IO) {
            dataLoader.getUsers().forEach { UserController.insertUser(it) }
            println("Users loaded.")
        }

        val job2 = launch(Dispatchers.IO) {
            dataLoader.getMaquinas().forEach { MaquinaController.insertMaquina(it) }
            println("Maquinas loaded.")
        }

        val job3 = launch(Dispatchers.IO) {
            dataLoader.getProductos().forEach { ProductoController.insertProducto(it) }
            println("Productos loaded.")
        }

        println()
        while (!job1.isCompleted || !job2.isCompleted ||
            !job3.isCompleted) {
            for (i in 1..3) {
                print(".")
                delay(500)
            }
            println()
        }

        joinAll(job1,job2,job3)

        /*
        println("////////////////////// TODAS ///////////////////////")
        println(MaquinaController.findAllMaquinas())
        println("//////////////// PERSONALIZADORAS //////////////////")
        println(PersonalizadoraController.findAllPersonalizadoras())
        println("////////////////// ENCORDADORAS ////////////////////")
        println(EncordadoraController.findAllEncordadoras())
        println("////////////////// FIN ////////////////////")
        */

        val job4 = launch(Dispatchers.IO) {
            dataLoader.getTareas().forEach { TareaController.insertTarea(it) }
            println("Tareas loaded.")
            dataLoader.getTurnos().forEach { TurnoController.insertTurno(it) }
            println(TurnoController.findAllTurnos())
            println("Turnos loaded.")
            dataLoader.getPedidos().forEach { PedidoController.insertPedido(it) }
            println("Pedidos loaded.")
        }
        println()
        while (!job4.isCompleted) {
            for (i in 1..3) {
                print(".")
                delay(500)
            }
            println()
        }

        job4.join()

        println("Data successfully loaded.")
        val prt1 = launch(Dispatchers.IO) { println("USERS: ${UserController.findAllUsers()}") }
        val prt2 = launch(Dispatchers.IO) { println("MAQUINAS: ${MaquinaController.findAllMaquinas()}") }
        val prt3 = launch(Dispatchers.IO) { println("PRODUCTOS: ${ProductoController.findAllProductos()}") }
        val prt4 = launch(Dispatchers.IO) { println("TAREAS: ${TareaController.findAllTareas()}") }
        val prt5 = launch(Dispatchers.IO) { println("TURNOS: ${TurnoController.findAllTurnos()}") }
        val prt6 = launch(Dispatchers.IO) { println("PEDIDOS: ${PedidoController.findAllPedidos()}") }
        joinAll(prt1,prt2,prt3,prt4,prt5,prt6)

        var loginEnter = ""
        println(" - Welcome. Do you want to log in or register? [login/register]")
        while (!loginEnter.contentEquals("login") && ! loginEnter.contentEquals("register")) {
            loginEnter = readln()
        }
        val user = if (loginEnter.contentEquals("login")) login() else register()

        launch { menu(user) }
    }
}

fun initDB() {
    val appConfig = AppConfig.fromPropertiesFile("${System.getProperty("user.dir")}${File.separator}" +
            "src${File.separator}main${File.separator}resources${File.separator}config.properties")
    println("Configuration: $appConfig")
    DataBaseManager.init(appConfig)
}
