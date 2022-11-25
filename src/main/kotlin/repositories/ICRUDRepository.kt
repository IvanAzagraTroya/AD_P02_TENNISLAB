package repositories

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface ICRUDRepository<T, ID> {
    suspend fun readAll(): Flow<T>
    suspend fun findById(id: ID): Deferred<T?>
    suspend fun create(entity: T): Deferred<T>
    suspend fun delete(entity: T): Deferred<Boolean>
}