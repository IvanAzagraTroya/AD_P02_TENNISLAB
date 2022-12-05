package repositories

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Interfaz basica de los repositorios que sirve como contrato
 * de que los repositorios deberan tener por lo menos las operaciones
 * CRUD basicas para su modelo y tipo de ID.
 */
interface ICRUDRepository<T, ID> {
    suspend fun readAll(): Flow<T>
    suspend fun findById(id: ID): Deferred<T?>
    suspend fun create(entity: T): Deferred<T>
    suspend fun delete(entity: T): Deferred<Boolean>
}