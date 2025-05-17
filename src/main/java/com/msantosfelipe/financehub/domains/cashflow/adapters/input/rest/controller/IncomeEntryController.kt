package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.CreateIncomeEntryDTO
import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.UpdateIncomeEntryDTO
import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.input.IncomeEntryServicePort
import com.msantosfelipe.financehub.shared.adapters.rest.conversions.Conversions
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import java.time.YearMonth
import java.util.UUID

@Controller("api/v1/incomes")
class IncomeEntryController(
    val incomeEntryService: IncomeEntryServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createIncomeEntry(
        @Body incomeEntryRequest: CreateIncomeEntryDTO,
    ): UUID =
        incomeEntryService.createIncomeEntry(
            IncomeEntry(
                referenceDate = YearMonth.parse(incomeEntryRequest.referenceDate).atEndOfMonth(),
                grossAmount = incomeEntryRequest.grossAmount,
                discountAmount = incomeEntryRequest.discountAmount,
                netAmount = incomeEntryRequest.netAmount,
                type = incomeEntryRequest.type,
                description = incomeEntryRequest.description,
            ),
        )

    @Put(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateIncomeEntry(
        @PathVariable uuid: UUID,
        @Body incomeEntryRequest: UpdateIncomeEntryDTO,
    ): IncomeEntry {
        val income = incomeEntryService.getIncomeEntryById(uuid)
        return incomeEntryService.updateIncomeEntry(
            incomeEntry =
                income.copy(
                    referenceDate = income.referenceDate,
                    grossAmount = incomeEntryRequest.grossAmount,
                    discountAmount = incomeEntryRequest.discountAmount,
                    netAmount = incomeEntryRequest.netAmount,
                    type = incomeEntryRequest.type,
                    description = incomeEntryRequest.description,
                ),
        )
    }

    @Delete(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteIncomeEntry(
        @PathVariable uuid: UUID,
    ) = incomeEntryService.deleteIncomeEntry(uuid)

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listIncomeEntriesByDateRange(
        @QueryValue initDate: String,
        @QueryValue endDate: String?,
    ): List<IncomeEntry> {
        val (init, end) = Conversions.parseYearMonthDates(initDate, endDate)
        return incomeEntryService.listIncomeEntriesByDateRange(init, end)
    }
}
