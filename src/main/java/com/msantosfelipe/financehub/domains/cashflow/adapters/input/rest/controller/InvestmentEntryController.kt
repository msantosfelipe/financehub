package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.CreateInvestmentEntryDTO
import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.UpdateInvestmentEntryDTO
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentEntry
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentGroupByMonth
import com.msantosfelipe.financehub.domains.cashflow.ports.input.InvestmentEntryServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.input.InvestmentServicePort
import com.msantosfelipe.financehub.shared.adapters.rest.conversions.Conversions
import com.msantosfelipe.financehub.shared.exceptions.GenericAlreadyExistsException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import java.time.YearMonth
import java.util.UUID

@Controller("api/v1/investments/entry")
class InvestmentEntryController(
    val investmentEntryService: InvestmentEntryServicePort,
    val investmentService: InvestmentServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createInvestmentEntry(
        @Body investmentEntryRequest: CreateInvestmentEntryDTO,
    ): UUID {
        val investment =
            investmentService.getInvestmentById(
                investmentEntryRequest.investmentId,
            )
        return investmentEntryService.createInvestmentEntry(
            InvestmentEntry(
                referenceDate = YearMonth.parse(investmentEntryRequest.referenceDate).atEndOfMonth(),
                investment = investment,
                amount = investmentEntryRequest.amount,
            ),
        )
    }

    @Put(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateInvestmentEntry(
        @PathVariable uuid: UUID,
        @Body investmentEntryRequest: UpdateInvestmentEntryDTO,
    ): InvestmentEntry {
        val investmentEntry = investmentEntryService.getInvestmentEntryById(uuid)
        val investment =
            investmentService.getInvestmentById(
                investmentEntryRequest.investmentId,
            )
        return investmentEntryService.updateInvestmentEntry(
            investmentEntry =
                investmentEntry.copy(
                    investment = investment,
                    amount = investmentEntryRequest.amount,
                ),
        )
    }

    @Delete(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteInvestmentEntry(
        @PathVariable uuid: UUID,
    ) = investmentEntryService.deleteInvestmentEntry(uuid)

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listInvestmentEntriesByDateRange(
        @QueryValue initDate: String,
        @QueryValue endDate: String?,
    ): List<InvestmentGroupByMonth> {
        val (init, end) = Conversions.parseYearMonthDates(initDate, endDate)
        return investmentEntryService.listInvestmentEntriesByDateRange(init, end)
    }

    @Error(global = true)
    fun handleConversionError(
        request: HttpRequest<*>,
        ex: ConversionErrorException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Unknown conversion error"
        return HttpResponse.badRequest(
            ErrorDto("ConversionError. $message"),
        )
    }

    @Error()
    fun handleAlreadyExistsException(
        request: HttpRequest<*>,
        ex: GenericAlreadyExistsException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Already exists error"
        return HttpResponse.badRequest(
            ErrorDto(message),
        )
    }

    @Error()
    fun handleNotFoundException(
        request: HttpRequest<*>,
        ex: GenericNotFoundException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Not found error"
        return HttpResponse.badRequest(
            ErrorDto(message),
        )
    }
}
