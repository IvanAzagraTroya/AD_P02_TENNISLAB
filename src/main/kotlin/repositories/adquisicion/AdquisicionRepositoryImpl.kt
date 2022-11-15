package repositories.adquisicion

import entities.ProductoDao
import models.Adquisicion
import org.jetbrains.exposed.dao.UUIDEntityClass

class AdquisicionRepositoryImpl(
    private val productosDao: UUIDEntityClass<ProductoDao>
): IAdquisicionRepository {
    override fun create(entity: Adquisicion): Adquisicion {
        TODO("Not yet implemented")
    }

    override fun readAll(): List<Adquisicion> {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Adquisicion): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(entity: Adquisicion): Adquisicion {
        TODO("Not yet implemented")
    }
}