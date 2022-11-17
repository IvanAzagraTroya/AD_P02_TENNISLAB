package repositories.adquisicion

import entities.AdquisicionDao
import entities.ProductoDao
import mappers.fromAdquisicionDaoToAdquisicion
import models.Adquisicion
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AdquisicionRepositoryImpl(
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val adquisicionDao: UUIDEntityClass<AdquisicionDao>
): IAdquisicionRepository {
    override fun create(entity: Adquisicion): Adquisicion = transaction {
        val existe = adquisicionDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Adquisicion): Adquisicion {
        return adquisicionDao.new(entity.id) {
            productoAdquirido = productoDao.findById(entity.productoAdquirido.id) ?: throw Exception()
            precio = entity.precio
        }.fromAdquisicionDaoToAdquisicion()
    }

    private fun update(entity: Adquisicion, existe: AdquisicionDao): Adquisicion {
        return existe.apply {
            productoAdquirido = productoDao.findById(entity.productoAdquirido.id) ?: throw Exception()
            precio = entity.precio
        }.fromAdquisicionDaoToAdquisicion()
    }

    override fun readAll(): List<Adquisicion> = transaction {
        adquisicionDao.all().map { it.fromAdquisicionDaoToAdquisicion() }
    }

    override fun findById(id: UUID): Adquisicion? = transaction {
        adquisicionDao.findById(id)?.fromAdquisicionDaoToAdquisicion()
    }

    override fun delete(entity: Adquisicion): Boolean = transaction() {
        val existe = adquisicionDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}