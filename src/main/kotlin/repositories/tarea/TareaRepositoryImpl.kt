package repositories.tarea

import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import mappers.fromProductoDaoToProducto
import mappers.fromTareaDaoToTarea
import mappers.fromUserDaoToUser
import models.Tarea
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TareaRepositoryImpl(
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>
): ITareaRepository {
    override fun readAll(): List<Tarea> = transaction {
        tareaDao.all().map { it.fromTareaDaoToTarea(
            it.raqueta.fromProductoDaoToProducto(),
            it.user.fromUserDaoToUser())
        }
    }

    override fun findById(id: UUID): Tarea? = transaction {
        val tdao = tareaDao.findById(id)
        tdao?.fromTareaDaoToTarea(
            tdao.raqueta.fromProductoDaoToProducto(),
            tdao.user.fromUserDaoToUser()
        )
    }

    override fun create(entity: Tarea): Tarea {
        val existe = tareaDao.findById(entity.id)
        return existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun update(entity: Tarea, existe: TareaDao): Tarea = transaction {
        existe.apply {
            raqueta = productoDao.findById(entity.raqueta.id) ?: throw Exception()
            precio = entity.precio
            user = userDao.findById(entity.user.id) ?: throw Exception()
            tipoTarea = entity.tipoTarea.toString()
        }.fromTareaDaoToTarea(entity.raqueta, entity.user)
    }

    fun insert(entity: Tarea): Tarea = transaction {
        tareaDao.new(entity.id) {
            raqueta = productoDao.findById(entity.raqueta.id) ?: throw Exception()
            precio = entity.precio
            user = userDao.findById(entity.user.id) ?: throw Exception()
            tipoTarea = entity.tipoTarea.toString()
        }.fromTareaDaoToTarea(entity.raqueta, entity.user)
    }

    override fun delete(entity: Tarea): Boolean = transaction {
        val existe = tareaDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}