package mappers

import entities.EncordadoraDao
import entities.MaquinaDao
import entities.PersonalizadoraDao
import models.*

/**
 * @author Iván Azagra Troya
 * Este Kotlin.file crea las funciones que recogen las entidades DAO
 * de las diferentes máquinas para devolver la clase Maquina POKO
 */

// TODO al pasarle por parámetro el atributo puede dar error al tener que ser introducido desde otro punto de la app
fun MaquinaDao.fromMaquinaDaoToMaquina(fromMaquinaDao: Maquina) {
    if(fromMaquinaDao is Encordadora){
        fun EncordadoraDao.fromEncordadoraDaoToEncordadora(): Encordadora {
            return Encordadora(
                id = id.value,
                isManual = isManual,
                maxTension = maxTension,
                minTension = minTension
            )
        }
    }
    else if(fromMaquinaDao is Personalizadora){
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

/*fun EncordadoraDao.fromEncordadoraDaoToEncordadora(): Encordadora {
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
}*/
