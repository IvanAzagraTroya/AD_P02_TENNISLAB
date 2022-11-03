import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
//    Por algún motivo solo deja llamar al Database desde main
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

}
