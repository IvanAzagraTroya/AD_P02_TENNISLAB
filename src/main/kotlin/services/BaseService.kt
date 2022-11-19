package services

import repositories.ICRUDRepository

abstract class BaseService<T, ID, R : ICRUDRepository<T, ID>>(rep: R) {
    val repository = rep

    fun findAll(): List<T> {
        return repository.readAll()
    }

    fun findById(id: ID): T? {
        return repository.findById(id)
    }

    fun insert(t: T): T {
        return repository.create(t)
    }

    fun delete(t: T): Boolean {
        return repository.delete(t)
    }
}