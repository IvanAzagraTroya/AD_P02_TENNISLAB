package db

import models.*
import models.enums.PedidoEstado
import models.enums.TipoProducto
import java.time.LocalDate


fun getUsers() = listOf(
    User(
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        contraseña = "estacontraseñanoestaensha512",
        perfil = "WORKER"
    ),
    User(
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        contraseña = "contra",
        perfil = "CLIENT"
    ),
    User(
        nombre = "Admin",
        apellido = "Administrador",
        telefono = "000000000",
        email = "admin@email.com",
        contraseña = "admin",
        perfil = "ADMIN"
    )
)
// todo hasta que no esté hecho lo de los horarios no puedo hacer esto
fun getWorkers() = listOf(
    Worker(
        maquina = Maquina(),

    )
)

fun getProducts() = listOf(
    Producto(
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "unaMarcaDePrueba",
        modelo = "unModeloDePrueba",
        precio = 12.3,
        stock = 5
    ),
    Producto(
        tipoProducto = TipoProducto.ANTIVIBRADORES,
        marca = "antivibradorBranding",
        modelo = "antivibradorModel",
        precio = 5.3,
        stock = 23
    ),
    Producto(
        tipoProducto = TipoProducto.FUNDAS,
        marca = "fundaBrand",
        modelo = "fundaModel",
        precio = 30.0,
        stock = 10
    )
)

fun getPersonalizadora() = listOf(
    Personalizadora(
        measuresManeuverability = false,
        measuresBalance = true,
        measuresRigidity = true
    ),
    Personalizadora(
        measuresManeuverability = true,
        measuresBalance = false,
        measuresRigidity = true
    ),
    Personalizadora(
        measuresManeuverability = false,
        measuresBalance = true,
        measuresRigidity = false
    )
)

fun getMaquina() = listOf(
    Maquina(
        modelo = "unModelitoMaquinon",
        marca = "unaMarcaMaquinona",
        fecha = LocalDate.now(),
        numeroSerie = "1123abc45de"
    ),
    Maquina(
        modelo = "unModelitoMaquinon2",
        marca = "unaMarcaMaquinona2",
        fecha = LocalDate.now(),
        numeroSerie = "2123abc45de2"
    ),
    Maquina(
        modelo = "unModelitoMaquinon3",
        marca = "unaMarcaMaquinona3",
        fecha = LocalDate.now(),
        numeroSerie = "3123abc45de3"
    )
)

fun getEncordadora() = listOf(
    Encordadora(
        isManual = false,
        maxTension = 12.7,
        minTension = 7.3
    ),
    Encordadora(
        isManual = true,
        maxTension = 20.0,
        minTension = 10.0
    ),
    Encordadora(
        isManual = true,
        maxTension = 15.2,
        minTension = 5.2
    ),
)

fun getPedidos() = listOf(
    Pedido(
        tareas = listOf<Tarea>(),
        client = User(),
        state = PedidoEstado.PROCESO,
        maquina = Maquina(),
        fechaEntrada = LocalDate.now(),
        fechaProgramada = LocalDate.of(2023, 3, 10),
        fechaSalida = LocalDate.of(2023, 3, 1),
        fechaEntrega = LocalDate.of(2023, 4, 1),
        precio = 200.5
    )
)
