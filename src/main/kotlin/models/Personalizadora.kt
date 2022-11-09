package models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*
import javax.swing.text.html.parser.Entity

data class Personalizadora(
    override var id: UUID = UUID.randomUUID()
    ):Maquina(id) {
    var measuresManeuverability: Boolean = false
    var measuresBalance: Boolean = false
    var measuresRigidity: Boolean = false
        constructor(measuresRigidity: Boolean, measuresManeuverability: Boolean, measuresBalance: Boolean
        ): this() {
            this.measuresRigidity = measuresRigidity
            this.measuresBalance = measuresBalance
            this.measuresManeuverability = measuresManeuverability

        }
    }
