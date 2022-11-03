package models

import java.util.*

data class Encordadora(
    override var id: UUID,
    val isManual: Boolean,
    val maxTension: Double,
    val minTension: Double
    ):Maquina(id)
