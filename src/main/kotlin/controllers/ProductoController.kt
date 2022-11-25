package controllers

import com.google.gson.GsonBuilder
import dto.ProductoDTO
import models.enums.TipoProducto
import services.ProductoService
import util.generateRespuesta
import java.util.*

object ProductoController {
    private val service = ProductoService()

    suspend fun findAllProductos(): String {
        return service.getAllProductos().toString()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllProductos())
            ?: "Error at ProductoController.findAllProductos"
        return "prueba"//generateRespuesta(result, "Error at ProductoController.findAllProductos")

         */
    }

    suspend fun findAllProductosDisponibles(): String {
        return service.getAllProductos().filter { it.stock > 0 }.toString()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllProductos().filter { it.stock > 0 })
            ?: "Error at ProductoController.findAllProductosDisponibles"
        return "prueba"//generateRespuesta(result, "Error at ProductoController.findAllProductosDisponibles")

         */
    }

    suspend fun getProductoById(id: UUID): String {
        return service.getProductoById(id)?.toJSON() ?: "Producto with id $id not found."
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getProductoById(id))
            ?: "Producto with id $id not found."
        return "prueba"//generateRespuesta(result, "Producto with id $id not found.")

         */
    }

    suspend fun getProductosByTipo(tipo: TipoProducto): String {
        return service.getAllProductos().filter { it.tipoProducto == tipo }.toString()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllProductos().filter { it.tipoProducto == tipo })
            ?: "Error at ProductoController.getProductosByTipo with tipo: $tipo"
        return "prueba"//generateRespuesta(result, "Error at ProductoController.getProductosByTipo with tipo: $tipo")

         */
    }

    suspend fun insertProducto(dto: ProductoDTO): String {
        return service.createProducto(dto).toJSON()
        /*
        val result = GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createProducto(dto))
            ?: "Could not insert Producto with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not insert Producto with id ${dto.id}")

         */
    }

    suspend fun deleteProducto(dto: ProductoDTO): String {
        val result = service.deleteProducto(dto)
        return if (result) dto.toJSON()
        else "Could not delete Producto with id ${dto.id}"
        /*
        val result =GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteProducto(dto))
            ?: "Could not delete Producto with id ${dto.id}"
        return "prueba"//generateRespuesta(result, "Could not delete Producto with id ${dto.id}")

         */
    }
}