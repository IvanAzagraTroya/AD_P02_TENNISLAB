package models

import java.util.*

/**
 * @author Iván Azagra Troya
 * Clase abstracta de la entidad tarea con un identificador
 * y el producto que se pasará
 */

abstract class Tarea(
    open var id: UUID = UUID.randomUUID(),
    ){
//    lateinit var producto: Producto
    private lateinit var producto: List<Producto> // Creo que si la relación es 0..N
//    puede haber entre 0 y varios, por lo que debería ser una lista de productos y no uno solo
    constructor(producto: List<Producto>) : this(){
        this.producto = producto
    }
}
