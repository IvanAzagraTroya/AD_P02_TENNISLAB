package mappers

import entities.AdquisicionDao
import entities.EncordadoDao
import entities.PersonalizacionDao
import entities.TareaDao
import models.Adquisicion
import models.Encordado
import models.Personalizacion
import models.Tarea


/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea las funciones que recogen las entidades DAO de las diferentes tareas
 * para devolver la clase
 */

// TODO al pasarle por parámetro el atributo puede dar error al tener que ser introducido desde otro punto de la app

fun EncordadoDao.fromEncordadoDaoToEncordado(): Encordado {
    return Encordado(
        id = id.value,
        tensionHorizontal = tensionHorizontal,
        cordajeHorizontal = cordajeHorizontal.fromProductoDaoToProducto(),
        tensionVertical = tensionVertical,
        cordajeVertical = cordajeVertical.fromProductoDaoToProducto(),
        dosNudos = dosNudos
    )
}

fun PersonalizacionDao.fromPersonalizacionDaoToPersonalizacion(): Personalizacion {
    return Personalizacion(
        id = id.value,
        peso = peso,
        balance = balance,
        rigidez = rigidez
    )
}

fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(): Adquisicion {
    return Adquisicion(
        id = id.value,
        productoAdquirido = productoAdquirido.fromProductoDaoToProducto(),
        precio = precio
    )
}