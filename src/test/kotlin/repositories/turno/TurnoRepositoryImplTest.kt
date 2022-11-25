package repositories.turno

import config.AppConfig
import controllers.MaquinaController
import controllers.ProductoController
import controllers.TareaController
import controllers.UserController
import db.DataBaseManager
import db.DataLoader
import entities.MaquinaDao
import entities.TareaDao
import entities.TurnoDao
import entities.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.*
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TurnoRepositoryImplTest {
    private val repository = TurnoRepositoryImpl(TurnoDao, UserDao, MaquinaDao, TareaDao)
    val raqueta = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3)
    val producto1 = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ac"),
        tipoProducto = TipoProducto.ANTIVIBRADORES,
        marca = "MarcaX",
        modelo = "ModeloX",
        precio = 10.5,
        stock = 5)
    val client = User(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839a"),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT.name)
    val tarea1 = Adquisicion(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ca"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto1,
        precio = producto1.precio)
    val maquina = Encordadora(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bc"),
        modelo = "ENC-4070Turbo",
        marca = "Genericosas Marca Registrada",
        fechaAdquisicion = LocalDate.parse("2021-10-01"),
        numeroSerie = "975318642Q",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1)
    val worker = User(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839c"),
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        password = "estacontraseñanoestaensha512",
        perfil = Profile.WORKER.name)
    val turno = Turno(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839b"),
        worker = worker,
        maquina = maquina,
        horaInicio = LocalDateTime.of(2022,10,14,10,59),
        horaFin = null,
        tarea1 = tarea1,
        tarea2 = null)

    /**
     * esto para inicializar los datos, ya que si intentas insertar un turno y,
     * por ejemplo, no está insertado el usuario que tiene ese turno, suelta excepcion,
     * pero eso ya depende de como tengais hecho el repositorio.
     *
     * Lo que si es imprtante de aqui es inicializar la base de datos.
     */
    companion object {
        @JvmStatic
        @BeforeAll
        // los runBlocking son necesarios si lo haceis con corrutinas,
        // ya que los tests no pueden ser funciones suspendidas.
        fun initialize() = runBlocking {
            // esta es la parte importante de inicializar la DB.
            val appConfig = AppConfig.fromPropertiesFile("${System.getProperty("user.dir")}${File.separator}" +
                    "src${File.separator}test${File.separator}resources${File.separator}config.properties")
            println("Configuration: $appConfig")
            DataBaseManager.init(appConfig)

            // y esto es cargado de datos.
            val dataLoader = DataLoader()
            println("Cargando datos iniciales...")
            val job1 = launch(Dispatchers.IO) { dataLoader.getUsers().forEach { UserController.insertUser(it) } }
            val job2 = launch(Dispatchers.IO) { dataLoader.getMaquinas().forEach { MaquinaController.insertMaquina(it) } }
            val job3 = launch(Dispatchers.IO) { dataLoader.getProductos().forEach { ProductoController.insertProducto(it) } }
            joinAll(job1,job2,job3)
            val job4 = launch(Dispatchers.IO) { dataLoader.getTareas().forEach { TareaController.insertTarea(it) } }
            job4.join()
            println("data loaded.")
        }
    }

    @BeforeEach
    // importante que lo que necesite lanzar una corrutina este dentro del runBlocking
    fun setUp() = runBlocking {
        repository.create(turno).await()
        // esto termina en un println porque si no considera que
        // queremos devolver el resultado de la funcion anterior,
        // lo cual no es el caso, no puede devolver nada.
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(turno).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(turno.id).await()
        // comparamos los ids porque los objetos, aunque iguales en contenido,
        // no son el mismo objeto, por lo que si los comparamos directamente, el test falla.
        assertEquals(turno.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val res = repository.readAll().toList().find { it.id == turno.id }
        assertEquals(turno.id, res?.id)
    }

    @Test
    @DisplayName("find by id inexistente")
    fun findByIdInexistente() = runBlocking {
        val res = repository.findById(UUID.fromString("00000000-0000-0000-0000-05b596ea83bc")).await()
        assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun save() = runBlocking {
        val res = repository.create(turno).await()
        assertEquals(turno.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(turno).await()
        val res2 = repository.findById(turno.id).await()
        assertAll( {
            assertNull(res2)
            assertTrue(res1)
        } )
    }
}