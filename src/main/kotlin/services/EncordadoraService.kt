package services

import dto.EncordadoraDTO
import entities.EncordadoraDao
import entities.MaquinaDao
import mappers.MaquinaMapper
import models.Encordadora
import models.Maquina
import models.enums.TipoMaquina
import repositories.encordadora.EncordadoraRepositoryImpl
import repositories.maquina.MaquinaRepositoryImpl
import java.util.*

class EncordadoraService: BaseService<Encordadora, UUID, EncordadoraRepositoryImpl>(EncordadoraRepositoryImpl(
    EncordadoraDao, MaquinaDao
)) {
    val maquinaRepo = MaquinaRepositoryImpl(MaquinaDao)
    val mapper = MaquinaMapper()

    fun getAllEncordadoras(): List<EncordadoraDTO> {
        return mapper.toEncordadoraDTO(this.findAll())
    }

    fun getEncordadoraById(id: UUID): EncordadoraDTO? {
        return this.findById(id)?.let { mapper.toEncordadoraDTO(it) }
    }

    fun createEncordadora(encordadora: EncordadoraDTO): EncordadoraDTO {
        val maquina = Maquina(
            id = encordadora.id,
            modelo = encordadora.modelo,
            marca = encordadora.marca,
            fechaAdquisicion = encordadora.fechaAdquisicion,
            numeroSerie = encordadora.numeroSerie,
            tipoMaquina = TipoMaquina.ENCORDADORA
        )
        maquinaRepo.create(maquina)
        return mapper.toEncordadoraDTO(this.insert(mapper.fromEncordadoraDTO(encordadora)))
    }

    fun deleteEncordadora(encordadora: EncordadoraDTO): Boolean {
        return this.delete(mapper.fromEncordadoraDTO(encordadora))
    }


    fun createEncordadoraInit(encordadora: EncordadoraDTO): EncordadoraDTO {
        val maquina = Maquina(
            id = encordadora.id,
            modelo = encordadora.modelo,
            marca = encordadora.marca,
            fechaAdquisicion = encordadora.fechaAdquisicion,
            numeroSerie = encordadora.numeroSerie,
            tipoMaquina = TipoMaquina.ENCORDADORA
        )
        maquinaRepo.insert(maquina)
        return mapper.toEncordadoraDTO(repository.insert(mapper.fromEncordadoraDTO(encordadora)))
    }
}