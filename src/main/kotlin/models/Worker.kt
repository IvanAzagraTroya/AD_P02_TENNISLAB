package models

import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase POKO de la entidad worker
 */

data class Worker(
    override var id: UUID = UUID.randomUUID(),
    ) :User(id) {
    var maquina: Maquina()
    var horaInicio: Int = 0
    var horaFin: Int = 0
    var numPedidos: Int = 0 // todo relación 0..2, por defecto hay 0 pedidos
    constructor(maquina: Maquina, numPedidos: Int) :this() {

    }
}
