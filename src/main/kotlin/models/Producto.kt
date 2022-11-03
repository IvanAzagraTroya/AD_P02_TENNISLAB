package models

import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase POKO de la entidad producto
 */
data class Producto(
    val id: UUID,
    val tipoProducto: TipoProducto,
    val marca: String,
    val modelo: String,
    val precio: Double,
    val stock: Int
    )

enum class TipoProducto{
    RAQUETAS,
    CORDAJES,
    OVERGRIPS,
    GRIPS,
    ANTIVIBRADORES,
    FUNDAS
}
