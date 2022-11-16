package repositories.pedidos

import entities.PedidoDao
import mappers.fromPedidoDaoToPedido
import models.Pedido
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PedidosRepositoryImpl(
    private val pedidoDao: UUIDEntityClass<PedidoDao>
): IPedidosRepository {
    override fun create(entity: Pedido): Pedido = transaction{
        val existe = pedidoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: kotlin.run {
            insert(entity)
        }
    }

    private fun insert(entity: Pedido): Pedido{
        return pedidoDao.new {
            tareas = entity.tareas, // TODO requiere SizeIterable<TareaDao>
            client = entity.client, // requiere userDao | se le pasa user
            turnos = entity.turnos, // todo requiere SizeIterable<TurnoDao> | se le pasa List<Turno>
            state = entity.state.toString(),
            fechaEntrada = entity.fechaEntrada,
            fechaProgramada = entity.fechaProgramada,
            fechaSalida = entity.fechaSalida,
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido()
    }

    private fun update(entity: Pedido, existe: PedidoDao): Pedido {
        return existe.apply {
            tareas = entity.tareas, // TODO requiere SizeIterable<TareaDao>
            client = entity.client, // requiere userDao | se le pasa user
            turnos = entity.turnos, // todo requiere SizeIterable<TurnoDao> | se le pasa List<Turno>
            state = entity.state.toString(),
            fechaEntrada = entity.fechaEntrada,
            fechaProgramada = entity.fechaProgramada,
            fechaSalida = entity.fechaSalida,
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido()
    }

    override fun readAll(): List<Pedido> {
        pedidoDao.all().map { it.fromPedidoDaoToPedido() }
    }

    override fun findById(id: UUID): Pedido? {
        pedidoDao.findById(id)?.fromPedidoDaoToPedido()
    }

    override fun update(entity: Pedido): Pedido {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Pedido): Boolean = transaction{
        val existe = pedidoDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}