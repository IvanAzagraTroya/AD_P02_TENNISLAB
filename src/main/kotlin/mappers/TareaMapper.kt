package mappers

import dto.*
import entities.*
import models.*
import models.enums.TipoTarea
import org.jetbrains.exposed.dao.UUIDEntityClass
import repositories.tarea.TareaRepositoryImpl


/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea las funciones que recogen las entidades DAO de las diferentes tareas
 * para devolver la clase
 */

// TODO al pasarle por parámetro el atributo puede dar error al tener que ser introducido desde otro punto de la app

fun TareaDao.fromTareaDaoToTarea(tareaDao: UUIDEntityClass<TareaDao>,
                                 productoDao: UUIDEntityClass<ProductoDao>,
                                 userDao: UUIDEntityClass<UserDao>): Tarea {
    return when (TipoTarea.parseTipoTarea(tipoTarea)) {
        TipoTarea.PERSONALIZACION -> PersonalizacionDao(id).fromPersonalizacionDaoToPersonalizacion(tareaDao, productoDao, userDao)
        TipoTarea.ENCORDADO -> EncordadoDao(id).fromEncordadoDaoToEncordado(tareaDao, productoDao, userDao)
        TipoTarea.ADQUISICION -> AdquisicionDao(id).fromAdquisicionDaoToAdquisicion(tareaDao, productoDao, userDao)
    }
}

fun PersonalizacionDao.fromPersonalizacionDaoToPersonalizacion(tareaDao: UUIDEntityClass<TareaDao>,
                                                               productoDao: UUIDEntityClass<ProductoDao>,
                                                               userDao: UUIDEntityClass<UserDao>): Personalizacion {
    val repo = TareaRepositoryImpl(tareaDao, productoDao, userDao)
    val tarea: Tarea = repo.findById(id.value) ?: throw Exception()
    return Personalizacion(
        id = id.value,
        raqueta = tarea.raqueta,
        user = tarea.user,
        peso = peso,
        balance = balance,
        rigidez = rigidez
    )
}

fun EncordadoDao.fromEncordadoDaoToEncordado(tareaDao: UUIDEntityClass<TareaDao>,
                                             productoDao: UUIDEntityClass<ProductoDao>,
                                             userDao: UUIDEntityClass<UserDao>): Encordado {
    val repo = TareaRepositoryImpl(tareaDao, productoDao, userDao)
    val tarea: Tarea = repo.findById(id.value) ?: throw Exception()
    return Encordado(
        id = id.value,
        raqueta = tarea.raqueta,
        user = tarea.user,
        tensionHorizontal = tensionHorizontal,
        cordajeHorizontal = cordajeHorizontal.fromProductoDaoToProducto(),
        tensionVertical = tensionVertical,
        cordajeVertical = cordajeVertical.fromProductoDaoToProducto(),
        dosNudos = dosNudos
    )
}

fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(tareaDao: UUIDEntityClass<TareaDao>,
                                                   productoDao: UUIDEntityClass<ProductoDao>,
                                                   userDao: UUIDEntityClass<UserDao>): Adquisicion {
    val repo = TareaRepositoryImpl(tareaDao, productoDao, userDao)
    val tarea: Tarea = repo.findById(id.value) ?: throw Exception()
    return Adquisicion(
        id = id.value,
        raqueta =  tarea.raqueta,
        user = tarea.user,
        productoAdquirido = productoAdquirido.fromProductoDaoToProducto(),
        precio = precio
    )
}


class TareaMapper:BaseMapper<Tarea, TareaDTO>() {
    override fun fromDTO(item: TareaDTO): Tarea {
        return when (item) {
            is PersonalizacionDTO -> fromPersonalizacionDTO(item)
            is EncordadoDTO -> fromEncordadoDTO(item)
            is AdquisicionDTO -> fromAdquisicionDTO(item)
            else -> throw Exception()
        }
    }

    fun fromAdquisicionDTO(item: AdquisicionDTO): Adquisicion {
        return Adquisicion(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            productoAdquirido = item.productoAdquirido,
            precio = item.precio
        )
    }

    fun fromEncordadoDTO(item: EncordadoDTO): Encordado {
        return Encordado(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            tensionHorizontal = item.tensionHorizontal,
            cordajeHorizontal = item.cordajeHorizontal,
            tensionVertical = item.tensionVertical,
            cordajeVertical = item.cordajeVertical,
            dosNudos = item.dosNudos
        )
    }

    fun fromPersonalizacionDTO(item: PersonalizacionDTO): Personalizacion {
        return Personalizacion(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            peso = item.peso,
            balance = item.balance,
            rigidez = item.rigidez
        )
    }

    override fun toDTO(item: Tarea): TareaDTO {
        return when (item) {
            is Personalizacion -> toPersonalizacionDTO(item)
            is Encordado -> toEncordadoDTO(item)
            is Adquisicion -> toAdquisicionDTO(item)
            else -> throw Exception()
        }
    }

    fun toAdquisicionDTO(item: Adquisicion): AdquisicionDTO {
        return AdquisicionDTO(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            productoAdquirido = item.productoAdquirido
        )
    }

    fun toEncordadoDTO(item: Encordado): EncordadoDTO {
        return EncordadoDTO(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            tensionHorizontal = item.tensionHorizontal,
            cordajeHorizontal = item.cordajeHorizontal,
            tensionVertical = item.tensionVertical,
            cordajeVertical = item.cordajeVertical,
            dosNudos = item.dosNudos
        )
    }

    fun toPersonalizacionDTO(item: Personalizacion): PersonalizacionDTO {
        return PersonalizacionDTO(
            id = item.id,
            raqueta = item.raqueta,
            user = item.user,
            peso = item.peso,
            balance = item.balance,
            rigidez = item.rigidez
        )
    }

    fun fromEncordadoDTO(items: List<EncordadoDTO>): List<Encordado> {
        return items.map { fromEncordadoDTO(it) }
    }
    fun toEncordadoDTO(items: List<Encordado>): List<EncordadoDTO> {
        return items.map { toEncordadoDTO(it) }
    }

    fun fromPersonalizacionDTO(items: List<PersonalizacionDTO>): List<Personalizacion> {
        return items.map { fromPersonalizacionDTO(it) }
    }
    fun toPersonalizacionDTO(items: List<Personalizacion>): List<PersonalizacionDTO> {
        return items.map { toPersonalizacionDTO(it) }
    }

    fun fromAdquisicionDTO(items: List<AdquisicionDTO>): List<Adquisicion> {
        return items.map { fromAdquisicionDTO(it) }
    }
    fun toAdquisicionDTO(items: List<Adquisicion>): List<AdquisicionDTO> {
        return items.map { toAdquisicionDTO(it) }
    }
}