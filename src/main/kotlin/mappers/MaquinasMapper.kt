package mappers

import models.*

/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea las funciones que recogen las entidades DAO
 * de las diferentes máquinas para devolver la clase Maquina POKO
 */

// No se puede hacer un fromMaquinasDaoToMaquinas porque la clase
// Maquina es abstracta, por lo que no puede instanciar un objeto de la misma

fun MaquinasDao.fromMaquinasDaoToMaquinas(fromMaquinasDao: Maquina) {
    if(fromMaquinasDao is Encordadora){
        fun EncordadoraDao.fromEncordadoraDaoToEncordadora(): Encordadora {
            return Encordadora(
                id = id.value,
                isManual = isManual,
                maxTension = maxTension,
                minTension = minTension
            )
        }
    }
    else if(fromMaquinasDao is Personalizadora){
        fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizadora(): Personalizadora{
            return Personalizadora(
                id = id.value,
                measuresManeuverability = measuresManeuverability,
                measuresBalance = measuresBalance,
                measuresRigidity = measuresRigidity
            )
        }
    }
}

fun EncordadoraDao.fromEncordadoraDaoToEncordadora(): Encordadora {
    return Encordadora(
        id = id.value,
        isManual = isManual,
        maxTension = maxTension,
        minTension = minTension
    )
}

fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizadora(): Personalizadora{
    return Personalizadora(
        id = id.value,
        measuresManeuverability = measuresManeuverability,
        measuresBalance = measuresBalance,
        measuresRigidity = measuresRigidity
    )
}
