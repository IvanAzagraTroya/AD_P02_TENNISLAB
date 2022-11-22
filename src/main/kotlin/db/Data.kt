package db

import dto.*
import mappers.*
import models.enums.PedidoEstado
import models.enums.Profile
import models.enums.TipoProducto
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class DataLoader {
    val pMapper = ProductoMapper()
    val uMapper = UserMapper()
    val mMapper = MaquinaMapper()
    val tMapper = TareaMapper()
    val turMapper = TurnoMapper()

    val raqueta = ProductoDTO(
        id = null,
        tipoProducto = TipoProducto.RAQUETAS,
        marca = "MarcaRaqueta",
        modelo = "ModeloRaqueta",
        precio = 150.5,
        stock = 3
    )

    val producto1 = ProductoDTO(
        id = null,
        tipoProducto = TipoProducto.ANTIVIBRADORES,
        marca = "MarcaX",
        modelo = "ModeloX",
        precio = 10.5,
        stock = 5
    )

    val producto2 = ProductoDTO(
        id = null,
        tipoProducto = TipoProducto.GRIPS,
        marca = "MarcaY",
        modelo = "ModeloY",
        precio = 20.7,
        stock = 10
    )

    val producto3 = ProductoDTO(
        id = null,
        tipoProducto = TipoProducto.FUNDAS,
        marca = "MarcaZ",
        modelo = "ModeloZ",
        precio = 36.4,
        stock = 8
    )

    val cordaje = ProductoDTO(
        id = null,
        tipoProducto = TipoProducto.CORDAJES,
        marca = "CordajEx",
        modelo = "C945-Alpha",
        precio = 53.1,
        stock = 4
    )

    val client = UserDTO(
        id = UUID.randomUUID(),
        nombre = "Maria",
        apellido = "Martinez",
        telefono = "632120281",
        email = "email2@email.com",
        password = "contra",
        perfil = Profile.CLIENT
    )

    val worker = UserDTO(
        id = UUID.randomUUID(),
        nombre = "Luis",
        apellido = "Martinez",
        telefono = "632950281",
        email = "email@email.com",
        password = "estacontraseñanoestaensha512",
        perfil = Profile.WORKER
    )

    val personalizadora1 = PersonalizadoraDTO(
        id = null,
        modelo = "RTX-3080TI",
        marca = "Nvidia",
        fechaAdquisicion = null,
        numeroSerie = "123456789X",
        measuresRigidity = false,
        measuresBalance = true,
        measuresManeuverability = true
    )

    val encordadora1 = EncordadoraDTO(
        id = UUID.randomUUID(),
        modelo = "ENC-4070Turbo",
        marca = "Genericosas Marca Registrada",
        fechaAdquisicion = LocalDate.now(),
        numeroSerie = "975318642Q",
        isManual = true,
        maxTension = 15.2,
        minTension = 5.1
    )

    val adquisicion1 = AdquisicionDTO(
        id = null,
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        productoAdquirido = pMapper.fromDTO(producto1)
    )

    val adquisicion2 = AdquisicionDTO(
        id = null,
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        productoAdquirido = pMapper.fromDTO(producto2)
    )

    val adquisicion3 = AdquisicionDTO(
        id = null,
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        productoAdquirido = pMapper.fromDTO(producto3)
    )

    val encordado = EncordadoDTO(
        id = null,
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        tensionHorizontal = 25.5,
        cordajeHorizontal = pMapper.fromDTO(cordaje),
        tensionVertical = 23.1,
        cordajeVertical = pMapper.fromDTO(cordaje),
        dosNudos = true
    )

    val personalizacion = PersonalizacionDTO(
        id = null,
        raqueta = pMapper.fromDTO(raqueta),
        user = uMapper.fromDTO(client),
        peso = 890,
        balance = 15.4,
        rigidez = 4
    )

    fun getUsers() = listOf(
        worker,
        client,
        UserDTO(
            id = UUID.randomUUID(),
            nombre = "Admin",
            apellido = "Administrador",
            telefono = "000000000",
            email = "admin@email.com",
            password = "admin",
            perfil = Profile.ADMIN
        )
    )

    fun getMaquinas() = listOf<MaquinaDTO>(
        personalizadora1,
        PersonalizadoraDTO(
            id = UUID.randomUUID(),
            modelo = "RX-480",
            marca = "Sapphire Radeon",
            fechaAdquisicion = LocalDate.now(),
            numeroSerie = "111111111A",
            measuresRigidity = true,
            measuresBalance = true,
            measuresManeuverability = true
        ),
        EncordadoraDTO(
            id = null,
            modelo = "ENC-2022XT",
            marca = "EncordadorasSL",
            fechaAdquisicion = null,
            numeroSerie = "124356879W",
            isManual = false,
            maxTension = 25.5,
            minTension = 9.7
        ),
        encordadora1
    )

    fun getProductos() = listOf(
        raqueta, producto1, producto2, producto3
    )

    fun getTareas() = listOf<TareaDTO>(
        adquisicion1,
        adquisicion2,
        adquisicion3,
        encordado,
        personalizacion
    )

    fun getTurnos() = listOf<TurnoDTO>(
        TurnoDTO(
            id = null,
            worker = uMapper.fromDTO(worker),
            maquina = mMapper.fromDTO(personalizadora1),
            horaInicio = LocalDateTime.now(),
            horaFin = null,
            tarea1 = tMapper.fromDTO(personalizacion),
            tarea2 = tMapper.fromDTO(adquisicion1)
        ),
        TurnoDTO(
            id = null,
            worker = uMapper.fromDTO(worker),
            maquina = mMapper.fromDTO(encordadora1),
            horaInicio = LocalDateTime.now().minusDays(1L),
            horaFin = null,
            tarea1 = tMapper.fromDTO(encordado),
            tarea2 = tMapper.fromDTO(adquisicion2)
        ),
        TurnoDTO(
            id = null,
            worker = uMapper.fromDTO(worker),
            maquina = mMapper.fromDTO(personalizadora1),
            horaInicio = LocalDateTime.now(),
            horaFin = null,
            tarea1 = tMapper.fromDTO(adquisicion3),
            tarea2 = null
        )
    )

    fun getPedidos() = listOf<PedidoDTO>(
        PedidoDTO(
            id = null,
            tareas = tMapper.fromDTO(getTareas()),
            client = uMapper.fromDTO(client),
            turnos = turMapper.fromDTO(getTurnos()),
            state = PedidoEstado.PROCESO,
            fechaEntrada = null,
            fechaProgramada = getTurnos().maxBy { it.horaFin }
                .horaFin.toLocalDate() ?: LocalDate.now(),
            fechaSalida = getTurnos().maxBy { it.horaFin }
                .horaFin.plusDays(5L).toLocalDate() ?: LocalDate.now(),
            fechaEntrega = null
        )
    )
}