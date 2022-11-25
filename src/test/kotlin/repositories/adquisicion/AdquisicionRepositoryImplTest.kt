package repositories.adquisicion

import config.AppConfig
import controllers.MaquinaController
import controllers.ProductoController
import controllers.UserController
import db.DataBaseManager
import db.DataLoader
import entities.AdquisicionDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Adquisicion
import models.Producto
import models.User
import models.enums.Profile
import models.enums.TipoProducto
import org.junit.jupiter.api.*
import repositories.tarea.TareaRepositoryImpl
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdquisicionRepositoryImplTest {
    private val repository = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    private val aRepo = AdquisicionRepositoryImpl(AdquisicionDao, TareaDao, ProductoDao)
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
    val producto3 = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83aa"),
        tipoProducto = TipoProducto.FUNDAS,
        marca = "MarcaZ",
        modelo = "ModeloZ",
        precio = 36.4,
        stock = 8
    )
    val adquisicion = Adquisicion(
        id = UUID.fromString("93a98d69-6da6-48a7-9999-05b596ea83cc"),
        raqueta = raqueta,
        user = client,
        productoAdquirido = producto3,
        precio = producto3.precio
    )

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
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(adquisicion).await()
        aRepo.create(adquisicion).await()
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(adquisicion).await()
        aRepo.delete(adquisicion).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = aRepo.findById(adquisicion.id).await()
        assertEquals(adquisicion.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = aRepo.readAll().toList()
        val res = list.find { it.id == adquisicion.id }
        assertEquals(adquisicion.id, res?.id)
    }

    @Test
    @DisplayName("find by id inexistente")
    fun findByIdInexistente() = runBlocking {
        val res = aRepo.findById(UUID.fromString("93a98d69-0000-1112-0000-05b596ea83ba")).await()
        assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        val res = aRepo.create(adquisicion).await()
        assertEquals(adquisicion.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = aRepo.delete(adquisicion).await()
        val res2 = aRepo.findById(adquisicion.id).await()
        assertAll( {
            assertNull(res2)
            assertTrue(res1)
        } )
    }
}