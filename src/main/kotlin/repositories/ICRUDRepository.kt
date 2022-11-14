package repositories

interface ICRUDRepository<T, ID> {
    fun create(entity: T): T
    fun readAll(): List<T>
    fun delete(entity: T): Boolean
    fun update(entity: T): T
}