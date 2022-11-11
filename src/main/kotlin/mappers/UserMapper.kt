package mappers

/*
import entities.UsersDao
import entities.WorkerDao
import models.User
import models.Worker

/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea la función que recoge las entidades DAO
 * para devolver la entidades de la clase User
 */

fun UsersDao.fromUsersDaoToUser(): User {
    return User(
        id = id.value,
        nombre = nombre,
        apellido =apellido,
        telefono = telefono,
        email = email,
        contraseña = contraseña,
        perfil = perfil
    )
}

// todo frommaquinadao devuelve unit y no sé por qué!!!!!!!!
fun WorkerDao.fromWorkerDaoToWorker(): Worker {
    return Worker(
        id = id.value,
        maquina = maquina.fromMaquinasDaoToMaquinas(),
        horaInicio = horaInicio,
        horaFin = horaFin,
        numPedidos = numPedidos

    )
}

 */