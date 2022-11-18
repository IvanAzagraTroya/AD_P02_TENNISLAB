package repositories.pedidos

import entities.*
import mappers.fromPedidoDaoToPedido
import models.Pedido
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PedidoRepositoryImpl(
    private val pedidoDao: UUIDEntityClass<PedidoDao>,
    private val userDao: UUIDEntityClass<UserDao>,
    private val maquinaDao: UUIDEntityClass<MaquinaDao>,
    private val productoDao: UUIDEntityClass<ProductoDao>,
    private val tareaDao: UUIDEntityClass<TareaDao>
): IPedidoRepository {
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

    override fun readAll(): List<Pedido> = transaction {
        pedidoDao.all().map { it.fromPedidoDaoToPedido(tareaDao, productoDao, userDao, maquinaDao) }
    }

    override fun findById(id: UUID): Pedido? = transaction {
        pedidoDao.findById(id)?.fromPedidoDaoToPedido(tareaDao, productoDao, userDao, maquinaDao)
    }

    override fun delete(entity: Pedido): Boolean = transaction{
        val existe = pedidoDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }
}