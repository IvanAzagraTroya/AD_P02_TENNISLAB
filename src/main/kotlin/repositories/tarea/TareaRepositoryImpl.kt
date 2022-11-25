package repositories.tarea

import entities.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromProductoDaoToProducto
import mappers.fromTareaDaoToTarea
import mappers.fromUserDaoToUser
import models.Adquisicion
import models.Encordado
import models.Personalizacion
import models.Tarea
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.encordado.EncordadoRepositoryImpl
import repositories.personalizacion.PersonalizacionRepositoryImpl
import java.util.*

class TareaRepositoryImpl(
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>
): ITareaRepository {
    override suspend fun readAll(): Flow<Tarea> = newSuspendedTransaction(Dispatchers.IO) {
        tareaDao.all().map { it.fromTareaDaoToTarea(/*tareaDao*/) }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Tarea?> = suspendedTransactionAsync(Dispatchers.IO) {
        val tdao = tareaDao.findById(id)
        tdao?.fromTareaDaoToTarea(/*tareaDao*/)
    }

    override suspend fun create(entity: Tarea): Deferred<Tarea> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = tareaDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun update(entity: Tarea, existe: TareaDao): Tarea {
        existe.apply {
            raqueta = productoDao.findById(entity.raqueta.id) ?: throw Exception()
            precio = entity.precio
            user = userDao.findById(entity.user.id) ?: throw Exception()
            tipoTarea = entity.tipoTarea.toString()
        }
        /*
        when (entity) {
            is Adquisicion -> aRepo.create(entity).await()
            is Encordado -> eRepo.create(entity).await()
            is Personalizacion -> pRepo.create(entity).await()
        }
        return tarea.fromTareaDaoToTarea(
            tareaDao, adquisicionDao, personalizacionDao, encordadoDao, productoDao, userDao
        )
        */
        return entity
    }

    fun insert(entity: Tarea): Tarea {
        tareaDao.new(entity.id) {
            raqueta = productoDao.findById(entity.raqueta.id) ?: throw Exception()
            precio = entity.precio
            user = userDao.findById(entity.user.id) ?: throw Exception()
            tipoTarea = entity.tipoTarea.toString()
        }
        /*
        when (entity) {
            is Adquisicion -> aRepo.create(entity).await()
            is Encordado -> eRepo.create(entity).await()
            is Personalizacion -> pRepo.create(entity).await()
        }
        return tarea.fromTareaDaoToTarea(
            tareaDao, adquisicionDao, personalizacionDao, encordadoDao, productoDao, userDao
        )

         */
        return entity
    }

    override suspend fun delete(entity: Tarea): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = tareaDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}