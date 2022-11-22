package repositories.encordado

import entities.EncordadoDao
import entities.ProductoDao
import entities.TareaDao
import entities.UserDao
import mappers.fromEncordadoDaoToEncordado
import models.Encordado
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class EncordadoRepositoryImpl(
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val encordadoDao: UUIDEntityClass<EncordadoDao>
):IEncordadoRepository {
    override fun readAll(): List<Encordado> = transaction {
        encordadoDao.all().map { it.fromEncordadoDaoToEncordado(tareaDao, productoDao, userDao) }
    }

    override fun findById(id: UUID): Encordado? = transaction {
        encordadoDao.findById(id)?.fromEncordadoDaoToEncordado(tareaDao, productoDao, userDao)
    }

    override fun create(entity: Encordado): Encordado {
        val existe = encordadoDao.findById(entity.id)
        return existe?.let { update(entity, it) } ?: run { insert(entity) }
    }

    fun insert(entity: Encordado): Encordado = transaction {
        encordadoDao.new(entity.id) {
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeHorizontal = productoDao.findById(entity.cordajeHorizontal.id) ?: throw Exception()
            cordajeVertical = productoDao.findById(entity.cordajeVertical.id) ?: throw Exception()
            dosNudos = entity.dosNudos
            precio = entity.precio
        }.fromEncordadoDaoToEncordado(entity.raqueta, entity.user)
    }

    private fun update(entity: Encordado, existe: EncordadoDao): Encordado = transaction {
        existe.apply {
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeHorizontal = productoDao.findById(entity.cordajeHorizontal.id) ?: throw Exception()
            cordajeVertical = productoDao.findById(entity.cordajeVertical.id) ?: throw Exception()
            dosNudos = entity.dosNudos
            precio = entity.precio
        }.fromEncordadoDaoToEncordado(entity.raqueta, entity.user)
    }

    override fun delete(entity: Encordado): Boolean = transaction {
        val existe = encordadoDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}