package util

import com.google.gson.GsonBuilder
import dto.Respuesta
import org.abstractj.kalium.crypto.Hash
import org.abstractj.kalium.encoders.Encoder.HEX
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


fun Double.toLocalMoney(locale: Locale): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}

fun LocalDate.toLocalDate(locale: Locale): String {
    return this.format(
        DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
    )
}

fun Double.toLocalNumber(locale: Locale): String {
    return NumberFormat.getNumberInstance(locale).format(this)
}

fun encode(text: String): String {
    val hash = Hash()
    val tb = hash.sha512(text.toByteArray())
    return HEX.encode(tb)
}

fun generateRespuesta(result: String, errorMessage: String): String {
    val respuesta = Respuesta(
        code =
        if (result.contentEquals(errorMessage))
            1707
        else 0,
        body = result
    )
    return GsonBuilder().setPrettyPrinting().create()
        .toJson(respuesta)
}

fun betweenXandY(res: String, x: Int, y: Int): Boolean {
    val n = res.toIntOrNull() ?: return false
    return n in x..y
}
