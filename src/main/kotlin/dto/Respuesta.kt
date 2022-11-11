package dto

import java.time.LocalDateTime
import java.util.UUID

class Respuesta() {
    val id: UUID = UUID.randomUUID()
    var code: Int = 0
    lateinit var body: String
    val generatedAt: LocalDateTime = LocalDateTime.now()

    constructor(
        code: Int,
        body: String
    ) : this() {
        this.code = code
        this.body = body
    }
}