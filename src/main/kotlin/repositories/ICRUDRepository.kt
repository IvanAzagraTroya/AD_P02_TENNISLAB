package repositories

interface ICRUDRepository<T, ID> {
    fun readAll(): List<T>
    fun findById(id: ID): T?
    fun create(entity: T): T
    fun delete(entity: T): Boolean
}