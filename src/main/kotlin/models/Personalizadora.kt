package models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*
import javax.swing.text.html.parser.Entity

data class Personalizadora(
    override var id: UUID,
    val measuresManeuverability: Boolean,
    val measuresBalance: Boolean,
    val measuresRigidity: Boolean
    ):Maquina(id)
