package controllers

import com.google.gson.GsonBuilder
import dto.PedidoDTO
import dto.UserDTO
import models.enums.PedidoEstado
import services.PedidoService
import util.generateRespuesta
import java.util.*

object PedidoController {
    private val service = PedidoService()

    suspend fun findAllPedidos(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPedidos())
            ?: "Error at PedidoController.findAllPedidos"
        return "prueba"//generateRespuesta(result, "Error at PedidoController.findAllPedidos")
    }

    suspend fun findAllPedidosFromUser(user: UserDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPedidos().filter { it.client.id == user.id })
            ?: "Error at PedidoController.findAllPedidosFromUser ${user.nombre}"
        return "prueba"//generateRespuesta(result, "Error at PedidoController.findAllPedidosFromUser ${user.nombre}")
    }

    suspend fun findAllPedidosWithEstado(state: PedidoEstado): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPedidos().filter { it.state == state })
            ?: "Error at PedidoController.findAllPedidosWithEstado $state"
        return "prueba"//generateRespuesta(result, "Error at PedidoController.findAllPedidosWithEstado $state")
    }

    suspend fun getPedidoById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPedidoById(id))
            ?: "Pedido with id $id not found."
        return "prueba"//generateRespuesta(result, "Pedido with id $id not found.")
    }

    suspend fun insertPedido(dto: PedidoDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPedido(dto))
            ?: "Could not insert Pedido with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Pedido with id ${dto.id}")
    }

    suspend fun deletePedido(dto: PedidoDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePedido(dto))
            ?: "Could not delete Pedido with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Pedido with id ${dto.id}")
    }
}