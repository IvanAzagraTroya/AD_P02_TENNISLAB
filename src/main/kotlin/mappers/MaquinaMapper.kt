package mappers

import dto.EncordadoraDTO
import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import entities.EncordadoraDao
import entities.MaquinaDao
import entities.PersonalizadoraDao
import models.*
import models.enums.TipoMaquina
import org.jetbrains.exposed.dao.UUIDEntityClass
import repositories.maquina.MaquinaRepositoryImpl

/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea las funciones que recogen las entidades DAO
 * de las diferentes máquinas para devolver la clase Maquina POKO
 */

// No se puede hacer un fromMaquinasDaoToMaquinas porque la clase
// Maquina es abstracta, por lo que no puede instanciar un objeto de la misma

fun MaquinaDao.fromMaquinaDaoToMaquina(maquinaDao: UUIDEntityClass<MaquinaDao>): Maquina {
    return when (TipoMaquina.parseTipoMaquina(tipoMaquina)) {
        TipoMaquina.ENCORDADORA -> EncordadoraDao(id).fromEncordadoraDaoToEncordadora(maquinaDao)
        TipoMaquina.PERSONALIZADORA -> PersonalizadoraDao(id).fromPersonalizadoraDaoToPersonalizadora(maquinaDao)
    }
}

fun EncordadoraDao.fromEncordadoraDaoToEncordadora(maquinaDao: UUIDEntityClass<MaquinaDao>): Encordadora {
    val repo = MaquinaRepositoryImpl(maquinaDao)
    val maquina: Maquina = repo.findById(id.value) ?: throw Exception()
    return Encordadora(
        id = id.value,
        modelo = maquina.modelo,
        marca = maquina.marca,
        fechaAdquisicion = maquina.fechaAdquisicion,
        numeroSerie = maquina.numeroSerie,
        isManual = isManual,
        maxTension = maxTension,
        minTension = minTension
    )
}

fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizadora(maquinaDao: UUIDEntityClass<MaquinaDao>): Personalizadora{
    val repo = MaquinaRepositoryImpl(maquinaDao)
    val maquina: Maquina = repo.findById(id.value) ?: throw Exception()
    return Personalizadora(
        id = id.value,
        modelo = maquina.modelo,
        marca = maquina.marca,
        fechaAdquisicion = maquina.fechaAdquisicion,
        numeroSerie = maquina.numeroSerie,
        measuresManeuverability = measuresManeuverability,
        measuresBalance = measuresBalance,
        measuresRigidity = measuresRigidity
    )
}

class MaquinaMapper: BaseMapper<Maquina,MaquinaDTO>() {
    override fun fromDTO(item: MaquinaDTO): Maquina {
        return when (item) {
            is EncordadoraDTO ->
                Encordadora(
                    id = item.id,
                    modelo = item.modelo,
                    marca = item.marca,
                    fechaAdquisicion = item.fechaAdquisicion,
                    numeroSerie = item.numeroSerie,
                    isManual = item.isManual,
                    maxTension = item.maxTension,
                    minTension = item.minTension
                )
            is PersonalizadoraDTO ->
                Personalizadora(
                    id = item.id,
                    modelo = item.modelo,
                    marca = item.marca,
                    fechaAdquisicion = item.fechaAdquisicion,
                    numeroSerie = item.numeroSerie,
                    measuresRigidity = item.measuresRigidity,
                    measuresManeuverability = item.measuresManeuverability,
                    measuresBalance = item.measuresBalance
                )
            else -> throw Exception()
        }
    }

    override fun toDTO(item: Maquina): MaquinaDTO {
        return when (item) {
            is Personalizadora -> PersonalizadoraDTO(
                id = item.id,
                modelo = item.modelo,
                marca = item.marca,
                fechaAdquisicion = item.fechaAdquisicion,
                numeroSerie = item.numeroSerie,
                measuresManeuverability = item.measuresManeuverability,
                measuresBalance = item.measuresBalance,
                measuresRigidity = item.measuresRigidity
            )
            is Encordadora -> EncordadoraDTO(
                id = item.id,
                modelo = item.modelo,
                marca = item.marca,
                fechaAdquisicion = item.fechaAdquisicion,
                numeroSerie = item.numeroSerie,
                isManual = item.isManual,
                maxTension = item.maxTension,
                minTension = item.minTension
            )
            else -> throw Exception()
        }
    }
}