package models

import models.enums.TipoMaquina
import java.time.LocalDate
import java.util.UUID

/**
 * @author Daniel Rodriguez Muñoz
 *
 * Clase POKO de Personalizadora, que será
 * traducida a un Dao.
 */
class Personalizadora(): Maquina() {
    var measuresManeuverability: Boolean = false
    var measuresBalance: Boolean = false
    var measuresRigidity: Boolean = false
    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion: LocalDate?,
        numeroSerie: String,
        measuresRigidity: Boolean,
        measuresManeuverability: Boolean,
        measuresBalance: Boolean
    ): this() {
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.measuresRigidity = measuresRigidity
        this.measuresBalance = measuresBalance
        this.measuresManeuverability = measuresManeuverability
        this.tipoMaquina = TipoMaquina.PERSONALIZADORA
    }

    constructor(
        id: UUID?,
        measuresRigidity: Boolean,
        measuresManeuverability: Boolean,
        measuresBalance: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.measuresRigidity = measuresRigidity
        this.measuresBalance = measuresBalance
        this.measuresManeuverability = measuresManeuverability
        this.tipoMaquina = TipoMaquina.PERSONALIZADORA
    }
}
