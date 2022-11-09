package models

import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase abstracta de la entidad tarea con un identificador
 * y el producto que se pasará
 */
// TODO revisar el funcionamiento de esta clase
open class Tarea(){
    open lateinit var id: UUID
    lateinit var raqueta: Producto
    open var precio: Double = 0.0
    lateinit var user: User

    constructor(
        id: UUID?,
        raqueta: Producto,
        precio: Double?,
        user: User
    ) : this () {
        this.id = id ?: UUID.randomUUID()
        this.raqueta = raqueta
        this.precio = precio ?: 0.0
        this.user = user
    }
    /*
//    lateinit var producto: Producto
    private lateinit var producto: List<Producto> // Creo que si la relación es 0..N
//    puede haber entre 0 y varios, por lo que debería ser una lista de productos y no uno solo
    constructor(producto: List<Producto>) : this(){
        this.producto = producto
    }
     */
}
