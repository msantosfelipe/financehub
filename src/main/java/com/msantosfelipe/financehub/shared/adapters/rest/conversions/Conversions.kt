package com.msantosfelipe.financehub.shared.adapters.rest.conversions

import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeParseException

object Conversions {
    fun parseYearMonthDates(
        initDate: String,
        endDate: String?,
    ): Pair<LocalDate, LocalDate> {
        try {
            val init = YearMonth.parse(initDate).atEndOfMonth()
            val end = endDate?.let { YearMonth.parse(it).atEndOfMonth() } ?: YearMonth.now().atEndOfMonth()

            if (end.isBefore(init)) {
                throw HttpStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End date is before init date",
                )
            }

            return Pair(init, end)
        } catch (e: DateTimeParseException) {
            throw HttpStatusException(
                HttpStatus.BAD_REQUEST,
                e.message,
            )
        }
    }
}
