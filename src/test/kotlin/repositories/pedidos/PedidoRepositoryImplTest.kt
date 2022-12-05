package repositories.pedidos

import config.AppConfig
import controllers.*
import db.DataBaseManager
import db.DataLoader
import entities.PedidoDao
import entities.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.*
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.*
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * @author Ivan Azagra Troya
 *
 * Clase de testeo de integracion de PedidoRepository
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PedidoRepositoryImplTest {
    private val repository = PedidoRepositoryImpl(PedidoDao, UserDao)
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
        perfil = Profile.WORKER.name
    )
    val raqueta = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83ba"),
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3)
    val client = User(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea839a"),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT.name)
    val producto = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83aa"),
        tipoProducto = TipoProducto.FUNDAS,
        marca = "MarcaZ",
        modelo = "ModeloZ",
        precio = 36.4,
        stock = 8
    )
    val tarea = Adquisicion(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83cc"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto,
        precio = producto.precio
    )
    val turno = Turno(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea8acb"),
        worker = worker,
        maquina = maquina,
        horaInicio = LocalDateTime.of(2033,5,5,5,5),
        horaFin = null,
        tarea1 = tarea,
        tarea2 = null
    )
    val pedido = Pedido(
        id = UUID.fromString("93a98d69-1707-1234-1234-05b596ea8acc"),
        tareas = listOf(tarea),
        client = client,
        turnos = listOf(turno),
        state = PedidoEstado.PROCESO,
        fechaEntrada = LocalDate.parse("2023-10-10"),
        fechaProgramada = turno.horaFin.toLocalDate(),
        fechaSalida = turno.horaFin.plusDays(5L).toLocalDate(),
        fechaEntrega = null
    )

    /**
     * Inicializacion de la base de datos para testing y carga de datos necesarios.
     */
    companion object {
        @JvmStatic
        @BeforeAll
        fun initialize() = runBlocking {
            val appConfig = AppConfig.fromPropertiesFile("${System.getProperty("user.dir")}${File.separator}" +
                    "src${File.separator}test${File.separator}resources${File.separator}config.properties")
            println("Configuration: $appConfig")
            DataBaseManager.init(appConfig)
            val dataLoader = DataLoader()
            println("Cargando datos iniciales...")
            val job1 = launch(Dispatchers.IO) { dataLoader.getUsers().forEach { UserController.insertUser(it) } }
            val job2 = launch(Dispatchers.IO) { dataLoader.getMaquinas().forEach { MaquinaController.insertMaquina(it) } }
            val job3 = launch(Dispatchers.IO) { dataLoader.getProductos().forEach { ProductoController.insertProducto(it) } }
            joinAll(job1,job2,job3)
            val job4 = launch(Dispatchers.IO) {
                dataLoader.getTareas().forEach { TareaController.insertTarea(it) }
                dataLoader.getTurnos().forEach { TurnoController.insertTurno(it) }
            }
            job4.join()
            println("data loaded.")
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(pedido).await()
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(pedido).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(pedido.id).await()
        assertEquals(pedido.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val res = repository.readAll().toList().find { it.id == pedido.id }
        assertEquals(pedido.id, res?.id)
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
        val res = repository.create(pedido).await()
        assertEquals(pedido.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(pedido).await()
        val res2 = repository.findById(pedido.id).await()
        assertAll( {
            assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}