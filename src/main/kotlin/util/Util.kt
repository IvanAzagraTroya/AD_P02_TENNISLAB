package util

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
