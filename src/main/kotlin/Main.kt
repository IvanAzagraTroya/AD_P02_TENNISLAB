import config.AppConfig
import controllers.*
import db.*
import kotlinx.coroutines.*
import login.login
import login.register
import menu.menu
import org.jetbrains.exposed.sql.Database
import java.io.File

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
            dataLoader.getUsers().forEach { println(UserController.insertUser(it)) }
            println("Users loaded.")
        }

        /*
        val job2 = launch(Dispatchers.IO) {
            dataLoader.getMaquinas().forEach { println(MaquinaController.insertMaquina(it)) }
            println("Maquinas loaded.")
        }
        val job3 = launch(Dispatchers.IO) {
            dataLoader.getProductos().forEach { println(ProductoController.insertProducto(it)) }
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


         */
        job1.join()
        println(UserController.findAllUsers())
        /*
        job2.join()
        job3.join()

        job4 = launch(Dispatchers.IO) {
            dataLoader.getTareas().forEach { println(TareaController.insertTarea(it)) }
            println("Tareas loaded.")
            dataLoader.getTurnos().forEach { println(TurnoController.insertTurno(it)) }
            println("Turnos loaded.")
            dataLoader.getPedidos().forEach { println(PedidoController.insertPedido(it)) }
            println("Pedidos loaded.")
        }

        job4.join()


         */
        println("Data successfully loaded.")

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
