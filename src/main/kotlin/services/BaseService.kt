package services

import repositories.ICRUDRepository

abstract class BaseService<T, ID, R : ICRUDRepository<T, ID>>(rep: R) {
    protected val repository = rep

    fun findAll(): List<T> {
        return repository.readAll()
    }

    fun getById(id: ID): T {
        return repository.getById(id)
    }

    fun insert(t: T): T {
        return repository.create(t)
    }

    fun update(t: T): T {
        return repository.update(t)
    }

    fun delete(id: ID): Boolean {
        return repository.delete(id)
    }
}