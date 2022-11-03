package models

import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase POKO de la entidad worker
 */

data class Worker(
    override var id: UUID,
    val maquina: Maquina,
    val horaInicio: Int,
    val horaFin: Int,
    var numPedidos: Int = 0 // relación 0..2, por defecto hay 0 pedidos
    ) :User(id)
