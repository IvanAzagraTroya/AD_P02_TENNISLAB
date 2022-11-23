package repositories.encordado

import entities.EncordadoDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import kotlinx.coroutines.Dispatchers
import mappers.fromEncordadoDaoToEncordado
import models.Encordado
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class EncordadoRepositoryImpl(
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val encordadoDao: UUIDEntityClass<EncordadoDao>
):IEncordadoRepository {
    override suspend fun readAll(): List<Encordado> = newSuspendedTransaction(Dispatchers.IO) {
        encordadoDao.all().map { it.fromEncordadoDaoToEncordado(tareaDao, productoDao, userDao) }
    }

    override suspend fun findById(id: UUID): Encordado? = newSuspendedTransaction(Dispatchers.IO) {
        encordadoDao.findById(id)?.fromEncordadoDaoToEncordado(tareaDao, productoDao, userDao)
    }

    override suspend fun create(entity: Encordado): Encordado = newSuspendedTransaction(Dispatchers.IO) {
        val existe = encordadoDao.findById(entity.id)
        existe?.let { update(entity, it) } ?: run { insert(entity) }
    }

    fun insert(entity: Encordado): Encordado {
        return encordadoDao.new(entity.id) {
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeHorizontal = productoDao.findById(entity.cordajeHorizontal.id) ?: throw Exception()
            cordajeVertical = productoDao.findById(entity.cordajeVertical.id) ?: throw Exception()
            dosNudos = entity.dosNudos
            precio = entity.precio
        }.fromEncordadoDaoToEncordado(entity.raqueta, entity.user)
    }

    private fun update(entity: Encordado, existe: EncordadoDao): Encordado {
        return existe.apply {
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeHorizontal = productoDao.findById(entity.cordajeHorizontal.id) ?: throw Exception()
            cordajeVertical = productoDao.findById(entity.cordajeVertical.id) ?: throw Exception()
            dosNudos = entity.dosNudos
            precio = entity.precio
        }.fromEncordadoDaoToEncordado(entity.raqueta, entity.user)
    }

    override suspend fun delete(entity: Encordado): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val existe = encordadoDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}