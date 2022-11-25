package repositories.personalizadora

import config.AppConfig
import db.DataBaseManager
import entities.MaquinaDao
import entities.PersonalizadoraDao
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Personalizadora
import org.junit.jupiter.api.*
import repositories.maquina.MaquinaRepositoryImpl
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonalizadoraRepositoryImplTest {
    private val repository = MaquinaRepositoryImpl(MaquinaDao)
    private val pRepository = PersonalizadoraRepositoryImpl(PersonalizadoraDao, MaquinaDao)
    val maquina = Personalizadora(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83bb"),
        modelo = "RTX-3080TI",
        marca = "Nvidia",
        fechaAdquisicion = LocalDate.parse("2022-11-10"),
        numeroSerie = "123456789X",
        measuresRigidity = false,
        measuresBalance = true,
        measuresManeuverability = true
    )

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
        pRepository.create(maquina).await()
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(maquina).await()
        pRepository.delete(maquina).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = pRepository.findById(maquina.id).await()
        assertEquals(maquina.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = pRepository.readAll().toList()
        val res = list.find { it.id == maquina.id }
        assertEquals(maquina.id, res?.id)
    }

    @Test
    @DisplayName("find by id inexistente")
    fun findByIdInexistente() = runBlocking {
        val res = pRepository.findById(UUID.fromString("93a98d69-0000-1112-0000-05b596ea83ba")).await()
        assertNull(res)
    }

    @Test
    @DisplayName("insert")
    fun insert() = runBlocking {
        val res = pRepository.create(maquina).await()
        assertEquals(maquina.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = pRepository.delete(maquina).await()
        val res2 = pRepository.findById(maquina.id).await()
        assertAll( {
            assertNull(res2)
            assertTrue(res1)
        } )
    }
}