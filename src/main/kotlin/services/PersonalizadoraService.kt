package services

import dto.PersonalizadoraDTO
import entities.MaquinaDao
import entities.PersonalizadoraDao
import mappers.MaquinaMapper
import models.Maquina
import models.Personalizadora
import models.enums.TipoMaquina
import repositories.maquina.MaquinaRepositoryImpl
import repositories.personalizadora.PersonalizadoraRepositoryImpl
import java.util.UUID

class PersonalizadoraService: BaseService<Personalizadora, UUID, PersonalizadoraRepositoryImpl>(
    PersonalizadoraRepositoryImpl(
    PersonalizadoraDao, MaquinaDao
)) {
    val maquinaRepo = MaquinaRepositoryImpl(MaquinaDao)
    val mapper = MaquinaMapper()

    fun getAllPersonalizadoras(): List<PersonalizadoraDTO> {
        return mapper.toPersonalizadoraDTO(this.findAll())
    }

    fun getPersonalizadoraById(id: UUID): PersonalizadoraDTO? {
        return this.findById(id)?.let { mapper.toPersonalizadoraDTO(it) }
    }

    fun createPersonalizadora(personalizadora: PersonalizadoraDTO): PersonalizadoraDTO {
        val maquina = Maquina(
            id = personalizadora.id,
            modelo = personalizadora.modelo,
            marca = personalizadora.marca,
            fechaAdquisicion = personalizadora.fechaAdquisicion,
            numeroSerie = personalizadora.numeroSerie,
            tipoMaquina = TipoMaquina.PERSONALIZADORA
        )
        maquinaRepo.create(maquina)
        return mapper.toPersonalizadoraDTO(this.insert(mapper.fromPersonalizadoraDTO(personalizadora)))
    }

    fun deletePersonalizadora(personalizadora: PersonalizadoraDTO): Boolean {
        return this.delete(mapper.fromPersonalizadoraDTO(personalizadora))
    }


    fun createPersonalizadoraInit(personalizadora: PersonalizadoraDTO): PersonalizadoraDTO {
        val maquina = Maquina(
            id = personalizadora.id,
            modelo = personalizadora.modelo,
            marca = personalizadora.marca,
            fechaAdquisicion = personalizadora.fechaAdquisicion,
            numeroSerie = personalizadora.numeroSerie,
            tipoMaquina = TipoMaquina.PERSONALIZADORA
        )
        maquinaRepo.insert(maquina)
        return mapper.toPersonalizadoraDTO(repository.insert(mapper.fromPersonalizadoraDTO(personalizadora)))
    }
}