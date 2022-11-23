package repositories.pedidos

import entities.*
import kotlinx.coroutines.Dispatchers
import mappers.fromPedidoDaoToPedido
import models.Pedido
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PedidoRepositoryImpl(
    private val pedidoDao: UUIDEntityClass<PedidoDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>
): IPedidoRepository {
    override suspend fun create(entity: Pedido): Pedido = newSuspendedTransaction(Dispatchers.IO) {
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
            state = entity.state.toString()
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido(tareaDao, productoDao, userDao, maquinaDao)
    }

    private fun update(entity: Pedido, existe: PedidoDao): Pedido {
        return existe.apply {
            client = userDao.findById(entity.client.id) ?: throw Exception()
            state = entity.state.toString()
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            fechaEntrega = entity.fechaEntrega
        }.fromPedidoDaoToPedido(tareaDao, productoDao, userDao, maquinaDao)
    }

    override suspend fun readAll(): List<Pedido> = newSuspendedTransaction(Dispatchers.IO) {
        pedidoDao.all().map { it.fromPedidoDaoToPedido(tareaDao, productoDao, userDao, maquinaDao) }
    }

    override suspend fun findById(id: UUID): Pedido? = newSuspendedTransaction(Dispatchers.IO) {
        pedidoDao.findById(id)?.fromPedidoDaoToPedido(tareaDao, productoDao, userDao, maquinaDao)
    }

    override suspend fun delete(entity: Pedido): Boolean = newSuspendedTransaction(Dispatchers.IO){
        val existe = pedidoDao.findById(entity.id) ?: return@newSuspendedTransaction false
        existe.delete()
        true
    }
}