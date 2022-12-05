package db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import config.AppConfig
import entities.*
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

private val logger = KotlinLogging.logger {}

/**
 * @author Ivan Azagra Troya
 *
 * Controlador de la Base de Datos que usa Hikari.
 */
object DataBaseManager {
    lateinit var appConfig: AppConfig
    fun init(appConfig: AppConfig) {
        this.appConfig = appConfig
        logger.debug("Initializing database")
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = appConfig.jdbcUrl
            driverClassName = appConfig.jdbcDriverClassName
            username = appConfig.jdbcUserName
            password = appConfig.jdbcPassword
            maximumPoolSize = appConfig.jdbcMaximumPoolSize
        }

        val dataSource = HikariDataSource(hikariConfig)

        Database.connect(dataSource)
        logger.debug("Database initialized successfully")

        if (appConfig.jdbcCreateTables) {
            createTables()
        }
    }

    private fun createTables() = transaction {
        logger.debug("Creating tables")

        if (appConfig.jdbcshowSQL)
            addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            UserTable, MaquinaTable, EncordadoraTable, PersonalizadoraTable,
            ProductoTable, TareaTable, EncordadoTable, PersonalizacionTable,
            AdquisicionTable, TurnoTable, PedidoTable
        )
        logger.debug("Tables created")
    }
}