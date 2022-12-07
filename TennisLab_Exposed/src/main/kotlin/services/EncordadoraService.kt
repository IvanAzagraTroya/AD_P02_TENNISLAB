package services

import dto.EncordadoraDTO
import dto.MaquinaDTO
import entities.EncordadoraDao
import entities.MaquinaDao
import kotlinx.coroutines.flow.toList
import mappers.MaquinaMapper
import models.Encordadora
import models.Maquina
import models.enums.TipoMaquina
import repositories.encordadora.EncordadoraRepositoryImpl
import repositories.maquina.MaquinaRepositoryImpl
import java.util.*

/**
 * @author Ivan Azagra Troya
 *
 * Clase encargada de llamar a las operaciones del repositorio correspondientes y
 * pasar el resultado de las mismas a DTO usando para ello
 * el mapper y el repositorio de Encordado.
 */
class EncordadoraService: BaseService<Encordadora, UUID, EncordadoraRepositoryImpl>(EncordadoraRepositoryImpl(
    EncordadoraDao, MaquinaDao
)) {
    val maquinaRepo = MaquinaRepositoryImpl(MaquinaDao)
    val mapper = MaquinaMapper()

    suspend fun getAllEncordadoras(): List<EncordadoraDTO> {
        return mapper.toEncordadoraDTO(this.findAll().toList())
    }

    suspend fun getEncordadoraById(id: UUID): EncordadoraDTO? {
        return this.findById(id).await()?.let { mapper.toEncordadoraDTO(it) }
    }

    suspend fun createEncordadora(encordadora: EncordadoraDTO): MaquinaDTO {
        val maquina = Maquina(
            id = encordadora.id,
            modelo = encordadora.modelo,
            marca = encordadora.marca,
            fechaAdquisicion = encordadora.fechaAdquisicion,
            numeroSerie = encordadora.numeroSerie,
            tipoMaquina = TipoMaquina.ENCORDADORA
        )
        maquinaRepo.create(maquina).await()
        return mapper.toDTO(this.insert(mapper.fromEncordadoraDTO(encordadora)).await())
    }

    suspend fun deleteEncordadora(encordadora: EncordadoraDTO): Boolean {
        return this.delete(mapper.fromEncordadoraDTO(encordadora)).await()
    }
}