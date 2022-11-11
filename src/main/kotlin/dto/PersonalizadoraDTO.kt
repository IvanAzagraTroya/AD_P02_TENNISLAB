package dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import util.toLocalDate
import java.time.LocalDate
import java.util.*

class PersonalizadoraDTO() {
    lateinit var id: UUID
    lateinit var modelo: String
    lateinit var marca: String
    lateinit var fechaAdquisicion: LocalDate
    lateinit var numeroSerie: String
    var measuresManeuverability: Boolean = true
    var measuresBalance: Boolean = true
    var measuresRigidity: Boolean = true

    constructor(
        id: UUID?,
        modelo: String,
        marca: String,
        fechaAdquisicion: LocalDate?,
        numeroSerie: String,
        measuresManeuverability: Boolean,
        measuresBalance: Boolean,
        measuresRigidity: Boolean
    ) : this() {
        this.id = id ?: UUID.randomUUID()
        this.modelo = modelo
        this.marca = marca
        this.fechaAdquisicion = fechaAdquisicion ?: LocalDate.now()
        this.numeroSerie = numeroSerie
        this.measuresManeuverability = measuresManeuverability
        this.measuresBalance = measuresBalance
        this.measuresRigidity = measuresRigidity
    }

    fun fromJSON(json: String): PersonalizadoraDTO? {
        return Gson().fromJson(json, PersonalizadoraDTO::class.java)
    }

    fun toJSON(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

    override fun toString(): String {
        return "Maquina(id=$id, " +
                "tipo=Personalizadora, " +
                "modelo=$modelo, " +
                "marca=$marca, " +
                "fechaAdquisicion=${fechaAdquisicion.toLocalDate(Locale("es", "ES"))}, " +
                "numeroSerie=$numeroSerie, " +
                "measuresManeuverability=$measuresManeuverability, " +
                "measuresBalance=$measuresBalance, " +
                "measuresRigidity=$measuresRigidity)"
    }
}