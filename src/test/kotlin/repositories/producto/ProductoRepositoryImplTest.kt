package repositories.producto

import config.AppConfig
import db.DataBaseManager
import entities.ProductoDao
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.Producto
import models.enums.TipoProducto
import org.junit.jupiter.api.*
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductoRepositoryImplTest {
    private val repository = ProductoRepositoryImpl(ProductoDao)

    val producto = Producto(
        id = UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83aa"),
        tipoProducto = TipoProducto.FUNDAS,
        marca = "MarcaZ",
        modelo = "ModeloZ",
        precio = 36.4,
        stock = 8
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
        repository.create(producto).await()
        println("set up completed.")
    }

    @AfterEach
    fun tearDown() = runBlocking {
        repository.delete(producto).await()
        println("teared down successfully.")
    }

    @Test
    @DisplayName("find by id")
    fun findById() = runBlocking {
        val res = repository.findById(producto.id).await()
        assertEquals(producto.id, res?.id)
    }

    @Test
    @DisplayName("find all")
    fun findAll() = runBlocking {
        val list = repository.readAll().toList()
        val res = list.find { it.id == producto.id }
        assertEquals(producto.id, res?.id)
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
        val res = repository.create(producto).await()
        assertEquals(producto.id, res.id)
    }

    @Test
    @DisplayName("delete")
    fun delete() = runBlocking {
        val res1 = repository.delete(producto).await()
        val res2 = repository.findById(producto.id).await()
        assertAll( {
            assertNull(res2)
            Assertions.assertTrue(res1)
        } )
    }
}