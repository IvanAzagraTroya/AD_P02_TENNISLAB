package repositories.pedidos

import entities.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mappers.fromPedidoDaoToPedido
import models.Pedido
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PedidoRepositoryImpl(
    private val pedidoDao: UUIDEntityClass<PedidoDao>,
    private val userDao: UUIDEntityClass<UserDao>
): IPedidoRepository {
    override suspend fun create(entity: Pedido): Deferred<Pedido> = suspendedTransactionAsync(Dispatchers.IO) {
        val existe = pedidoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    fun insert(entity: Pedido): Pedido {
        // creamos el pedido
        //como la lista de tareas y turnos esta definida como val, ya que si no no dejaba meter el referrersOn, no la podemos cambiar ahora
        return pedidoDao.new(entity.id) {
            client = userDao.findById(entity.client.id) ?: throw Exception()
            tareas = entity.tareas.toString()
            turnos = entity.turnos.toString()
            state = entity.state.toString()
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido()
    }

    private fun update(entity: Pedido, existe: PedidoDao): Pedido {
        return existe.apply {
            client = userDao.findById(entity.client.id) ?: throw Exception()
            tareas = entity.tareas.toString()
            turnos = entity.turnos.toString()
            state = entity.state.toString()
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido()
    }

    override suspend fun readAll(): Flow<Pedido> = newSuspendedTransaction(Dispatchers.IO) {
        pedidoDao.all().map { it.fromPedidoDaoToPedido() }.asFlow()
    }

    override suspend fun findById(id: UUID): Deferred<Pedido?> = suspendedTransactionAsync(Dispatchers.IO) {
        pedidoDao.findById(id)?.fromPedidoDaoToPedido()
    }

    override suspend fun delete(entity: Pedido): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO){
        val existe = pedidoDao.findById(entity.id) ?: return@suspendedTransactionAsync false
        existe.delete()
        true
    }
}