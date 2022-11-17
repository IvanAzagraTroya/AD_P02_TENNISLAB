package repositories.pedidos

import entities.PedidoDao
import entities.TareaDao
import entities.TurnoDao
import entities.UserDao
import mappers.fromPedidoDaoToPedido
import models.Pedido
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PedidoRepositoryImpl(
    private val pedidoDao: UUIDEntityClass<PedidoDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val turnoDao: UUIDEntityClass<TurnoDao>
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
        return pedidoDao.new(entity.id) {
            tareas = tareaDao.all()
            client = userDao.findById(entity.client.id) ?: throw Exception()
            turnos = turnoDao.all()
            state = entity.state.toString()
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido()
    }

    private fun update(entity: Pedido, existe: PedidoDao): Pedido {
        return existe.apply {
            tareas = tareaDao.all()
            client = userDao.findById(entity.client.id) ?: throw Exception()
            turnos = turnoDao.all()
            state = entity.state.toString()
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido()
    }

    override fun readAll(): List<Pedido> = transaction {
        pedidoDao.all().map { it.fromPedidoDaoToPedido() }
    }

    override fun findById(id: UUID): Pedido? = transaction {
        pedidoDao.findById(id)?.fromPedidoDaoToPedido()
    }

    override fun delete(entity: Pedido): Boolean = transaction{
        val existe = pedidoDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}