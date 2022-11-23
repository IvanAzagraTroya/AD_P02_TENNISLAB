package repositories.tarea

import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import kotlinx.coroutines.Dispatchers
import mappers.fromProductoDaoToProducto
import mappers.fromTareaDaoToTarea
import mappers.fromUserDaoToUser
import models.Tarea
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TareaRepositoryImpl(
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>
): ITareaRepository {
    override suspend fun readAll(): List<Tarea> = newSuspendedTransaction(Dispatchers.IO) {
        tareaDao.all().map { it.fromTareaDaoToTarea(
            it.raqueta.fromProductoDaoToProducto(),
            it.user.fromUserDaoToUser())
        }
    }

    override suspend fun findById(id: UUID): Tarea? = newSuspendedTransaction(Dispatchers.IO) {
        val tdao = tareaDao.findById(id)
        tdao?.fromTareaDaoToTarea(
            tdao.raqueta.fromProductoDaoToProducto(),
            tdao.user.fromUserDaoToUser()
        )
    }

    override suspend fun create(entity: Tarea): Tarea = newSuspendedTransaction(Dispatchers.IO) {
        val existe = tareaDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun update(entity: Tarea, existe: TareaDao): Tarea {
        return existe.apply {
            raqueta = productoDao.findById(entity.raqueta.id) ?: throw Exception()
            precio = entity.precio
            user = userDao.findById(entity.user.id) ?: throw Exception()
            tipoTarea = entity.tipoTarea.toString()
        }.fromTareaDaoToTarea(entity.raqueta, entity.user)
    }

    fun insert(entity: Tarea): Tarea {
        return tareaDao.new(entity.id) {
            raqueta = productoDao.findById(entity.raqueta.id) ?: throw Exception()
            precio = entity.precio
            user = userDao.findById(entity.user.id) ?: throw Exception()
            tipoTarea = entity.tipoTarea.toString()
        }.fromTareaDaoToTarea(entity.raqueta, entity.user)
    }

    override suspend fun delete(entity: Tarea): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val existe = tareaDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}