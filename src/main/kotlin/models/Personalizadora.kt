package models

import java.util.*

data class Personalizadora(
    override var id: UUID,
    val measuresManeuverability: Boolean,
    val measuresBalance: Boolean,
    val measuresRigidity: Boolean
    ):Maquina(id)
