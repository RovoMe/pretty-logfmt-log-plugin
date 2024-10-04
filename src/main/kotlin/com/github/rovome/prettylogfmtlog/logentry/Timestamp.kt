package com.github.rovome.prettylogfmtlog.logentry

import com.ibm.icu.math.BigDecimal
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

sealed interface Timestamp {
    fun format(zoneId: ZoneId, formatter: DateTimeFormatter): String

    data class Parsed(val value: Instant) : Timestamp {
        override fun format(zoneId: ZoneId, formatter: DateTimeFormatter): String {
            return value.atZone(zoneId).format(formatter)
        }
    }

    data class Fallback(val value: String) : Timestamp {
        override fun format(zoneId: ZoneId, formatter: DateTimeFormatter): String {
            return value
        }
    }

    companion object {
        fun fromEpoch(value: Long): Parsed {
            return Parsed(
                    when {
                        value < 10_000_000_000L -> Instant.ofEpochSecond(value)
                        value < 10_000_000_000_000L -> Instant.ofEpochMilli(value)
                        value < 10_000_000_000_000_000L -> Instant.ofEpochSecond(
                                value / 1_000_000,
                                (value % 1_000_000) * 1_000
                        ) // microseconds
                        else -> Instant.ofEpochSecond(value / 1_000_000_000, value % 1_000_000_000) // nanoseconds
                    }
            )
        }

        fun fromString(value: String): Timestamp {
            if (value.contains(".") && value.contains("E")
                && value.indexOf(".") < value.indexOf("E")) {
                return Parsed(Instant.ofEpochMilli((BigDecimal(value).multiply(BigDecimal(1000))).toLong()))
            }
            return try {
                // Use OffsetDateTime.parse instead of Instant.parse because Instant.parse in JDK <= 11 does not support non-UTC offset like "-05:00".
                // See: https://stackoverflow.com/questions/68217689/how-to-use-instant-java-class-to-parse-a-date-time-with-offset-from-utc/68221614#68221614
                Parsed(OffsetDateTime.parse(value).toInstant())
            } catch (e: DateTimeParseException) {
                Fallback(value)
            }
        }
    }
}

private val timestampKeys = listOf("timestamp", "time", "@timestamp", "ts", "@t", "Timestamp")

fun extractTimestamp(node: Map<String, String>): Timestamp? {

    return timestampKeys.firstNotNullOfOrNull { node[it] }?.let { timestamp ->
        if (isNumeric(timestamp)) {
            // We assume that the number is a Unix timestamp in seconds, milliseconds, microseconds, or nanoseconds.
            Timestamp.fromEpoch(timestamp.toLong())
        } else {
            Timestamp.fromString(timestamp)
        }
    }
}

fun isNumeric(toCheck: String): Boolean {
    return toCheck.toLongOrNull() != null
}