package repositories.adquisicion

import entities.AdquisicionDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import kotlinx.coroutines.Dispatchers
import mappers.fromAdquisicionDaoToAdquisicion
import models.Adquisicion
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AdquisicionRepositoryImpl(
    private val adquisicionDao: UUIDEntityClass<AdquisicionDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>
): IAdquisicionRepository {
    override suspend fun create(entity: Adquisicion): Adquisicion = newSuspendedTransaction(Dispatchers.IO) {
        val existe = adquisicionDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    fun insert(entity: Adquisicion): Adquisicion {
        return adquisicionDao.new(entity.id) {
            productoAdquirido = productoDao.findById(entity.productoAdquirido.id) ?: throw Exception()
            precio = entity.precio
        }.fromAdquisicionDaoToAdquisicion(entity.raqueta, entity.user)
    }

    private fun update(entity: Adquisicion, existe: AdquisicionDao): Adquisicion {
        return existe.apply {
            productoAdquirido = productoDao.findById(entity.productoAdquirido.id) ?: throw Exception()
            precio = entity.precio
        }.fromAdquisicionDaoToAdquisicion(entity.raqueta, entity.user)
    }

    override suspend fun readAll(): List<Adquisicion> = newSuspendedTransaction(Dispatchers.IO) {
        adquisicionDao.all().map { it.fromAdquisicionDaoToAdquisicion(tareaDao, productoDao, userDao) }
    }

    override suspend fun findById(id: UUID): Adquisicion? = newSuspendedTransaction(Dispatchers.IO) {
        adquisicionDao.findById(id)?.fromAdquisicionDaoToAdquisicion(tareaDao, productoDao, userDao)
    }

    override suspend fun delete(entity: Adquisicion): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val existe = adquisicionDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}