package controllers

import com.google.gson.GsonBuilder
import dto.PedidoDTO
import services.PedidoService
import java.sql.SQLException
import java.util.*

object PedidoController {
    private val service = PedidoService()

    @Throws(SQLException::class)
    fun findAllPedidos(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPedidos())
            ?: throw SQLException("Error at PedidoController.findAllPedidos")
    }

    @Throws(SQLException::class)
    private fun getPedidoById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPedidoById(UUID.fromString(id)))
            ?: throw SQLException("Pedido with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getPedidoById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPedidoById(id))
            ?: throw SQLException("Pedido with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertPedido(dto: PedidoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPedido(dto))
            ?: throw SQLException("Could not insert Pedido with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updatePedido(dto: PedidoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updatePedido(dto))
            ?: throw SQLException("Could not update Pedido with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deletePedido(dto: PedidoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePedido(dto))
            ?: throw SQLException("Could not delete Pedido with id ${dto.id}")
    }
}