package menu

import dto.UserDTO
import models.enums.Profile
import util.betweenXandY

fun menu(user: UserDTO) {
    var back = false
    while (!back) {
        println(" - Please select one of the following actions.")
        when (user.perfil) {
            Profile.CLIENT -> {
                println("""
                1. Pedidos
                2. Productos
                3. Back
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 3)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> menuPedidos(Profile.CLIENT)
                    2 -> menuProductos(Profile.CLIENT)
                    3 -> back = true
                }
            }
            Profile.WORKER -> {
                println("""
                1. Pedidos
                2. Tareas
                3. Turnos
                4. Maquinas
                5. Back
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 5)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> menuPedidos(Profile.WORKER)
                    2 -> menuTareas(Profile.WORKER)
                    3 -> menuTurnos(Profile.WORKER)
                    4 -> menuMaquinas(Profile.WORKER)
                    5 -> back = true
                }
            }
            Profile.ADMIN -> {
                println("""
                1. Users
                2. Pedidos
                3. Tareas
                4. Turnos
                5. Productos
                6. Maquinas
                7. Back
                """.trimIndent())
                var res = ""
                while (!betweenXandY(res, 1, 7)) {
                    res = readln()
                }
                when (res.toInt()) {
                    1 -> menuUsers()
                    2 -> menuPedidos(Profile.ADMIN)
                    3 -> menuTareas(Profile.ADMIN)
                    4 -> menuTurnos(Profile.ADMIN)
                    5 -> menuProductos(Profile.ADMIN)
                    6 -> menuMaquinas(Profile.ADMIN)
                    7 -> back = true
                }
            }
        }
    }
}