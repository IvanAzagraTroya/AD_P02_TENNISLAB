package controllers

import com.google.gson.GsonBuilder
import dto.ProductoDTO
import java.sql.SQLException
import java.util.*

object ProductoController {
    /*
    private val service = ProductoService()

    @Throws(SQLException::class)
    fun findAllProductos(): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getAllProductos())
            ?: throw SQLException("Error at ProductoController.findAllProductos")
    }

    @Throws(SQLException::class)
    private fun getProductoById(id: String): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getProductoById(UUID.fromString(id)))
            ?: throw SQLException("Producto with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun getProductoById(id: UUID): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.getProductoById(id))
            ?: throw SQLException("Producto with id $id not found.")
    }

    @Throws(SQLException::class)
    private fun insertProducto(dto: ProductoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.createProducto(dto))
            ?: throw SQLException("Could not insert Producto with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun updateProducto(dto: ProductoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.updateProducto(dto))
            ?: throw SQLException("Could not update Producto with id ${dto.id}")
    }

    @Throws(SQLException::class)
    private fun deleteProducto(dto: ProductoDTO): String {
        return GsonBuilder().setPrettyPrinting().create()
            .toJson(service.deleteProducto(dto))
            ?: throw SQLException("Could not delete Producto with id ${dto.id}")
    }

     */
}