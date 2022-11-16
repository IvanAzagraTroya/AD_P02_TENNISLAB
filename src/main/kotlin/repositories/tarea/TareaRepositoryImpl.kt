package repositories.tarea

import entities.TareaDao
import models.Tarea
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TareaRepositoryImpl(
    private val tareaDao: UUIDEntityClass<TareaDao>
): ITareaRepository {
    override fun create(entity: Tarea): Tarea = transaction{
        val existe = tareaDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: kotlin.run {
            insert(entity)
        }
    }

    private fun insert(entity: Tarea): Tarea{
        return tareaDao.new {
            raqueta = entity.raqueta,
            precio = entity.precio,
            user = entity.user,
            tipoTarea = entity.tipoTarea
        }
    }

    override fun readAll(): List<Tarea> {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Tarea {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Tarea): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(entity: Tarea): Tarea {
        TODO("Not yet implemented")
    }
}