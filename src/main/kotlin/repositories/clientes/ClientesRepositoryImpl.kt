package repositories.clientes

/*
import entities.UsersDao
import mappers.fromUsersDaoToUser
import models.User
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ClientesRepositoryImpl(
    private val clientesDao: UUIDEntityClass<UsersDao>,
): IClientesRepository {
    override fun findAll(): List<User> = transaction {
        clientesDao.all().map { it.fromUsersDaoToUser() }
    }

    override fun findById(id: UUID): User? = transaction {
        clientesDao.findById(id)?.fromUsersDaoToUser()
    }

    override fun create(entity: User): User = transaction {
        val existe = clientesDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: kotlin.run {
            insert(entity)
        }
    }

    override fun update(entity: User): User {
        TODO("Not yet implemented")
    }

    private fun insert(entity: User): User{
        return clientesDao.new {
            nombre = entity.nombre
            apellido = entity.apellido
            telefono = entity.telefono
            email = entity.email
            contraseña = entity.contraseña
        }.fromUsersDaoToUser()
    }

    override fun readAll(): List<User> = transaction{
        clientesDao.all().map { it.fromUsersDaoToUser() }
    }


    private fun update(entity: User, existe: UsersDao): User {
        return existe.apply {
            nombre = entity.nombre
            apellido = entity.apellido
            telefono = entity.telefono
            email = entity.email
            contraseña = entity.contraseña
        }.fromUsersDaoToUser()
    }

    override fun delete(entity: User): Boolean = transaction{
        val existe = clientesDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}