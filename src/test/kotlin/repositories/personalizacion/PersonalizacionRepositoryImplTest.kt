package repositories.personalizacion

import config.AppConfig
import controllers.MaquinaController
import controllers.ProductoController
import controllers.UserController
import db.DataBaseManager
import db.DataLoader
import entities.PersonalizacionDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Personalizacion
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

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase de testeo de integracion de PersonalizacionRepository
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonalizacionRepositoryImplTest {
    private val repository = TareaRepositoryImpl(TareaDao, ProductoDao, UserDao)
    private val pRepo = PersonalizacionRepositoryImpl(PersonalizacionDao, TareaDao)
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
    val personalizacion = Personalizacion(
        id = UUID.fromString("93a98d69-6da6-48a7-1707-05b596ea8aab"),
        raqueta = raqueta,
        user = client,
        peso = 890,
        balance = 15.4,
        rigidez = 4)

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
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(personalizacion).await()
        pRepo.create(personalizacion).await()
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(personalizacion).await()
        pRepo.delete(personalizacion).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = pRepo.findById(personalizacion.id).await()
        assertEquals(personalizacion.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = pRepo.readAll().toList()
        val res = list.find { it.id == personalizacion.id }
        assertEquals(personalizacion.id, res?.id)
    }

    @Test
    @DisplayName("find by id inexistente")
    fun findByIdInexistente() = runBlocking {
        val res = pRepo.findById(UUID.fromString("93a98d69-0000-1112-0000-05b596ea83ba")).await()
        assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        val res = pRepo.create(personalizacion).await()
        assertEquals(personalizacion.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = pRepo.delete(personalizacion).await()
        val res2 = pRepo.findById(personalizacion.id).await()
        assertAll( {
            assertNull(res2)
            assertTrue(res1)
        } )
    }
}