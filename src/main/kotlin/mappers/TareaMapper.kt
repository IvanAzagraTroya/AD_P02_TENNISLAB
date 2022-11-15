package mappers

import dto.AdquisicionDTO
import dto.EncordadoDTO
import dto.PersonalizacionDTO
import dto.TareaDTO
import entities.*
import models.Adquisicion
import models.Encordado
import models.Personalizacion
import models.Tarea
import models.enums.TipoTarea
import repositories.tarea.TareaRepositoryImpl

fun TareaDao.fromTareaDaoToTarea(): Tarea {
    return when (TipoTarea.parseTipoTarea(tipoTarea)) {
        TipoTarea.PERSONALIZACION -> PersonalizacionDao(id).fromPersonalizacionDaoToPersonalizacion()
        TipoTarea.ENCORDADO -> EncordadoDao(id).fromEncordadoDaoToEncordado()
        TipoTarea.ADQUISICION -> AdquisicionDao(id).fromAdquisicionDaoToAdquisicion()
    }
}

fun PersonalizacionDao.fromPersonalizacionDaoToPersonalizacion(): Personalizacion {
    val repo = TareaRepositoryImpl()
    val tarea: Tarea = repo.findById(id.value)
    return Personalizacion(
        id = id.value,
        raqueta = tarea.raqueta,
        user = tarea.user,
        peso = peso,
        balance = balance,
        rigidez = rigidez
    )
}

fun EncordadoDao.fromEncordadoDaoToEncordado(): Encordado {
    val repo = TareaRepositoryImpl()
    val tarea: Tarea = repo.findById(id.value)
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

fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(): Adquisicion {
    val repo = TareaRepositoryImpl()
    val tarea: Tarea = repo.findById(id.value)
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
            is PersonalizacionDTO -> Personalizacion(
                id = item.id,
                raqueta = item.raqueta,
                user = item.user,
                peso = item.peso,
                balance = item.balance,
                rigidez = item.rigidez
            )
            is EncordadoDTO -> Encordado(
                id = item.id,
                raqueta = item.raqueta,
                user = item.user,
                tensionHorizontal = item.tensionHorizontal,
                cordajeHorizontal = item.cordajeHorizontal,
                tensionVertical = item.tensionVertical,
                cordajeVertical = item.cordajeVertical,
                dosNudos = item.dosNudos
            )
            is AdquisicionDTO -> Adquisicion(
                id = item.id,
                raqueta = item.raqueta,
                user = item.user,
                productoAdquirido = item.productoAdquirido,
                precio = item.precio
            )
            else -> throw Exception()
        }
    }

    override fun toDTO(item: Tarea): TareaDTO {
        return when (item) {
            is Personalizacion -> PersonalizacionDTO(
                id = item.id,
                raqueta = item.raqueta,
                user = item.user,
                peso = item.peso,
                balance = item.balance,
                rigidez = item.rigidez
            )
            is Encordado -> EncordadoDTO(
                id = item.id,
                raqueta = item.raqueta,
                user = item.user,
                tensionHorizontal = item.tensionHorizontal,
                cordajeHorizontal = item.cordajeHorizontal,
                tensionVertical = item.tensionVertical,
                cordajeVertical = item.cordajeVertical,
                dosNudos = item.dosNudos
            )
            is Adquisicion -> AdquisicionDTO(
                id = item.id,
                raqueta = item.raqueta,
                user = item.user,
                productoAdquirido = item.productoAdquirido
            )
            else -> throw Exception()
        }
    }
}