package controllers

import com.google.gson.GsonBuilder
import dto.ProductoDTO
import models.enums.TipoProducto
import services.ProductoService
import util.generateRespuesta
import java.util.*

object ProductoController {
    private val service = ProductoService()

    fun findAllProductos(): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllProductos())
            ?: "Error at ProductoController.findAllProductos"
        return generateRespuesta(result, "Error at ProductoController.findAllProductos")
    }

    fun getProductoById(id: UUID): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getProductoById(id))
            ?: "Producto with id $id not found."
        return generateRespuesta(result, "Producto with id $id not found.")
    }

    fun getProductosByTipo(tipo: TipoProducto): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllProductos().filter { it.tipoProducto == tipo })
            ?: "Error at ProductoController.getProductosByTipo with tipo: $tipo"
        return generateRespuesta(result, "Error at ProductoController.getProductosByTipo with tipo: $tipo")
    }

    fun insertProducto(dto: ProductoDTO): String {
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createProducto(dto))
            ?: "Could not insert Producto with id ${dto.id}"
        return generateRespuesta(result, "Could not insert Producto with id ${dto.id}")
    }

    fun deleteProducto(dto: ProductoDTO): String {
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteProducto(dto))
            ?: "Could not delete Producto with id ${dto.id}"
        return generateRespuesta(result, "Could not delete Producto with id ${dto.id}")
    }
}