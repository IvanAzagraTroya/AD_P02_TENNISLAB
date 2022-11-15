package mappers
import entities.ProductoDao
import models.Producto
import models.enums.TipoProducto

fun ProductoDao.fromProductoDaoToProducto(): Producto{
    return Producto(
        id = id.value,
        tipoProducto = TipoProducto.parseTipoProducto(tipoProducto),
        marca = marca,
        modelo = modelo,
        precio = precio,
        stock = stock
    )
}

