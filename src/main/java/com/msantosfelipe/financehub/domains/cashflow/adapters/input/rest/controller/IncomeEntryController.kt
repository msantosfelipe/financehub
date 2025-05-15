package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.IncomeEntryDTO
import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.input.IncomeEntryServicePort
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import java.time.YearMonth
import java.util.UUID

@Controller("api/v1/assets")
class IncomeEntryController(
    val incomeEntryService: IncomeEntryServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createIncomeEntry(
        @Body incomeEntryRequest: IncomeEntryDTO,
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
}
