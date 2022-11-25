package repositories.clientes

import config.AppConfig
import db.DataBaseManager
import entities.UserDao
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.User
import models.enums.Profile
import org.junit.jupiter.api.*
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryImplTest {
    private val repository = UserRepositoryImpl(UserDao)

    val admin = User(
        id = UUID.fromString("93a98d69-0000-1111-0000-05b596ea83ba"),
        nombre = "loli",
        apellido = "test",
        telefono = "123456789",
        email = "loli@test.com",
        password = "lolitest",
        perfil = Profile.ADMIN.name)

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
        repository.create(admin).await()
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(admin).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(admin.id).await()
        assertEquals(admin.id, res?.id)
    }

    @Test
    @DisplayName("find by email")
    fun findByMail() = runBlocking {
        val res = repository.findByEmail(admin.email).await()
        assertAll( {
            assertEquals(admin.id, res?.id)
            assertEquals(admin.email, res?.email)
        } )
    }

    @Test
    @DisplayName("find by phone")
    fun findByPhone() = runBlocking {
        val res = repository.findByPhone(admin.telefono).await()
        assertAll( {
            assertEquals(admin.id, res?.id)
            assertEquals(admin.telefono, res?.telefono)
        } )
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll().toList()
        val res = list.find { it.id == admin.id }
        assertEquals(admin.id, res?.id)
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
        val res = repository.create(admin).await()
        assertEquals(admin.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(admin).await()
        val res2 = repository.findById(admin.id).await()
        assertAll( {
            assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}