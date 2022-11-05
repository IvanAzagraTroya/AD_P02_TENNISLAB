package models.enums

/**
 * @author Iván Azagra Troya
 * Enumerador de los tipos de productos que puede haber junto a un
 * companion object que actúa como parser
 */
enum class TipoProducto(value: String){
    RAQUETAS("RAQUETAS"),
    CORDAJES("CORDAJES"),
    OVERGRIPS("OVERGRIPS"),
    GRIPS("GRIPS"),
    ANTIVIBRADORES("ANTIVIBRADORES"),
    FUNDAS("FUNDAS");

    companion object {
        fun parseTipoProducto(value: String): TipoProducto {
            return when (value) {
                "RAQUETAS" -> RAQUETAS
                "CORDAJES" -> CORDAJES
                "OVERGRIPS" -> OVERGRIPS
                "GRIPS" -> GRIPS
                "ANTIVIBRADORES" -> ANTIVIBRADORES
                "FUNDAS" -> FUNDAS
                else -> throw IllegalArgumentException("Ese tipo de producto no existe")
            }
        }
    }
}