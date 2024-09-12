package co.orange.core.extension

import java.text.BreakIterator
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")

fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")

fun String.getGraphemeLength(): Int {
    val breakIterator: BreakIterator = BreakIterator.getCharacterInstance()

    breakIterator.setText(this)

    var count = 0
    while (breakIterator.next() != BreakIterator.DONE) {
        count++
    }

    return count
}

fun String.breakLines(): String {
    return this.replace(" ", "\u00A0")
}

fun String.toPhoneFrom(): String? {
    return if (this.length == 11) {
        "${this.substring(0, 3)}-${this.substring(3, 7)}-${this.substring(7)}"
    } else {
        null
    }
}

fun String.convertDateTime(
    oldPattern: String,
    newPattern: String,
): String =
    LocalDateTime.parse(
        this.replace(Regex("\\.\\d{1,6}"), ""),
        DateTimeFormatter.ofPattern(oldPattern),
    )
        .format(DateTimeFormatter.ofPattern(newPattern))

fun String.convertToAge(oldPattern: String): Int =
    Period.between(
        LocalDate.parse(this, DateTimeFormatter.ofPattern(oldPattern)),
        LocalDate.now(),
    ).years + 1

fun String.maskName(): String =
    when (this.length) {
        2 -> "${this[0]}*"
        3 -> "${this[0]}*${this[2]}"
        else -> "${this[0]}" + "*".repeat(this.length - 2) + "${this.last()}"
    }
