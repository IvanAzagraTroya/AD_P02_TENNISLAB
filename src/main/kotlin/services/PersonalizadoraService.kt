package services

import dto.MaquinaDTO
import dto.PersonalizadoraDTO
import entities.EncordadoraDao
import entities.MaquinaDao
import entities.PersonalizadoraDao
import kotlinx.coroutines.flow.toList
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

    suspend fun getAllPersonalizadoras(): List<PersonalizadoraDTO> {
        return mapper.toPersonalizadoraDTO(this.findAll().toList())
    }

    suspend fun getPersonalizadoraById(id: UUID): PersonalizadoraDTO? {
        return this.findById(id).await()?.let { mapper.toPersonalizadoraDTO(it) }
    }

    suspend fun createPersonalizadora(personalizadora: PersonalizadoraDTO): MaquinaDTO {
        val maquina = Maquina(
            id = personalizadora.id,
            modelo = personalizadora.modelo,
            marca = personalizadora.marca,
            fechaAdquisicion = personalizadora.fechaAdquisicion,
            numeroSerie = personalizadora.numeroSerie,
            tipoMaquina = TipoMaquina.PERSONALIZADORA
        )
        maquinaRepo.create(maquina).await()
        return mapper.toDTO(this.insert(mapper.fromPersonalizadoraDTO(personalizadora)).await())
    }

    suspend fun deletePersonalizadora(personalizadora: PersonalizadoraDTO): Boolean {
        return this.delete(mapper.fromPersonalizadoraDTO(personalizadora)).await()
    }
}