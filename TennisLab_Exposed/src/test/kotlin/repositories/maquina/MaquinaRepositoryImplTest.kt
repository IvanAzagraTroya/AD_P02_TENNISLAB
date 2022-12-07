package repositories.maquina

import config.AppConfig
import db.DataBaseManager
import entities.EncordadoraDao
import entities.MaquinaDao
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Encordadora
import org.junit.jupiter.api.*
import repositories.encordadora.EncordadoraRepositoryImpl
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase de testeo de integracion de MaquinaRepository
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MaquinaRepositoryImplTest {
    private val repository = MaquinaRepositoryImpl(MaquinaDao)
    private val eRepository = EncordadoraRepositoryImpl(EncordadoraDao, MaquinaDao)
    val maquina = Encordadora(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bc"),
        modelo = "ENC-4070Turbo",
        marca = "Genericosas Marca Registrada",
        fechaAdquisicion = LocalDate.parse("2021-10-01"),
        numeroSerie = "975318642Q",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1)

    /**
     * Inicializacion de la base de datos para testing.
     */
    companion object {
        @JvmStatic
        @BeforeAll
        fun initialize() {
            val appConfig = AppConfig.fromPropertiesFile("${System.getProperty("user.dir")}${File.separator}" +
                    "src${File.separator}test${File.separator}resources${File.separator}config.properties")
            println("Configuration: $appConfig")
            DataBaseManager.init(appConfig)
        }
    }

    @BeforeEach
    fun setUp() = runBlocking {
        repository.create(maquina).await()
        eRepository.create(maquina).await()
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(maquina).await()
        eRepository.delete(maquina).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(maquina.id).await()
        assertEquals(maquina.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll().toList()
        val res = list.find { it.id == maquina.id }
        assertEquals(maquina.id, res?.id)
    }

    @Test
    @DisplayName("find by id inexistente")
    fun findByIdInexistente() = runBlocking {
        val res = repository.findById(UUID.fromString("93a98d69-0000-1112-0000-05b596ea83ba")).await()
        assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        val res = repository.create(maquina).await()
        assertEquals(maquina.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(maquina).await()
        val res2 = repository.findById(maquina.id).await()
        assertAll( {
            assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}