package config

import mu.KotlinLogging
import java.io.FileInputStream
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase que lee y carga el fichero config.properties
 */
class AppConfig(
    val nombre: String,
    val version: String,
    val jdbcUrl: String,
    val jdbcUserName: String,
    val jdbcPassword: String,
    val jdbcDriverClassName: String,
    val jdbcMaximumPoolSize: Int = 10,
    val jdbcCreateTables: Boolean = true,
    val jdbcshowSQL: Boolean = true,
) {
    companion object {
        val DEFAULT = AppConfig(
            nombre = "BD",
            version = "1.0.0",
            jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
            jdbcUserName = "username",
            jdbcPassword = "",
            jdbcDriverClassName = "org.h2.Driver",
            jdbcMaximumPoolSize = 10,
            jdbcCreateTables = true,
            jdbcshowSQL = true,
        )

        fun fromPropertiesFile(file: String): AppConfig {
            logger.debug { "Loading properties from $file" }
            val properties = Properties()
            properties.load(FileInputStream(file))
            return AppConfig(
                nombre = properties.getProperty("name"),
                version = properties.getProperty("version"),
                jdbcUrl = properties.getProperty("jdbc.url"),
                jdbcUserName = properties.getProperty("jdbc.username"),
                jdbcPassword = properties.getProperty("jdbc.password"),
                jdbcDriverClassName = properties.getProperty("jdbc.driverClassName"),
                jdbcMaximumPoolSize = properties.getProperty("jdbc.maximumPoolSize").toInt(),
                jdbcCreateTables = properties.getProperty("jdbc.createTables").toBoolean(),
                jdbcshowSQL = properties.getProperty("jdbc.showSQL").toBoolean()
            )
        }
    }
}