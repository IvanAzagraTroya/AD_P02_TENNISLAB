package repositories

interface ICRUDRepository<T, ID> {
    fun create(entity: T): T
    fun readAll(): List<T>

    fun delete(entity: T): Boolean
    fun getById(id: ID) : T
    fun update(entity: T): T
    fun delete(id: ID): Boolean

}