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

    fun findAllPedidos(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPedidos())
            ?: "Error at PedidoController.findAllPedidos"
        return generateRespuesta(result, "Error at PedidoController.findAllPedidos")
    }

    fun findAllPedidosFromUser(user: UserDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPedidos().filter { it.client.id == user.id })
            ?: "Error at PedidoController.findAllPedidosFromUser ${user.nombre}"
        return generateRespuesta(result, "Error at PedidoController.findAllPedidosFromUser ${user.nombre}")
    }

    fun findAllPedidosWithEstado(state: PedidoEstado): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllPedidos().filter { it.state == state })
            ?: "Error at PedidoController.findAllPedidosWithEstado $state"
        return generateRespuesta(result, "Error at PedidoController.findAllPedidosWithEstado $state")
    }

    fun getPedidoById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getPedidoById(id))
            ?: "Pedido with id $id not found."
        return generateRespuesta(result, "Pedido with id $id not found.")
    }

    fun insertPedido(dto: PedidoDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPedido(dto))
            ?: "Could not insert Pedido with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Pedido with id ${dto.id}")
    }

    fun deletePedido(dto: PedidoDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deletePedido(dto))
            ?: "Could not delete Pedido with id ${dto.id}"
        return generateRespuesta(result, "Could not delete Pedido with id ${dto.id}")
    }


    fun insertPedidoInit(dto: PedidoDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createPedidoInit(dto))
            ?: "Could not insert Pedido with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Pedido with id ${dto.id}")
    }
}