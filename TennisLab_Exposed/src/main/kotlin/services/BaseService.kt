package services

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import repositories.ICRUDRepository

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase abstracta que actua como base de los servicios.
 */
abstract class BaseService<T, ID, R : ICRUDRepository<T, ID>>(rep: R) {
    val repository = rep

    suspend fun findAll(): Flow<T> {
        return repository.readAll()
    }

    suspend fun findById(id: ID): Deferred<T?> {
        return repository.findById(id)
    }

    suspend fun insert(t: T): Deferred<T> {
        return repository.create(t)
    }

    suspend fun delete(t: T): Deferred<Boolean> {
        return repository.delete(t)
    }
}