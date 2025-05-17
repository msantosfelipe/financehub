package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.CreateInvestmentDTO
import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.UpdateInvestmentDTO
import com.msantosfelipe.financehub.domains.cashflow.domain.model.Investment
import com.msantosfelipe.financehub.domains.cashflow.domain.model.investmentTypeLabelsPTBR
import com.msantosfelipe.financehub.domains.cashflow.ports.input.InvestmentServicePort
import com.msantosfelipe.financehub.shared.adapters.rest.dto.EnumOptionDTO
import com.msantosfelipe.financehub.shared.exceptions.GenericAlreadyExistsException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import java.util.UUID

@Controller("api/v1/investments")
class InvestmentController(
    val investmentService: InvestmentServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createInvestment(
        @Body investmentRequest: CreateInvestmentDTO,
    ): UUID =
        investmentService.createInvestment(
            Investment(
                name = investmentRequest.name,
                type = investmentRequest.type,
                description = investmentRequest.description,
            ),
        )

    @Put(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateInvestment(
        @PathVariable uuid: UUID,
        @Body updateInvestmentRequest: UpdateInvestmentDTO,
    ): Investment {
        val investment = investmentService.getInvestmentById(uuid)
        return investmentService.updateInvestment(
            investment =
                investment.copy(
                    name = updateInvestmentRequest.name,
                    type = updateInvestmentRequest.type,
                    description = updateInvestmentRequest.description,
                    active = updateInvestmentRequest.active,
                ),
        )
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listActiveInvestments(): List<Investment> = investmentService.listActiveInvestments()

    @Get("types")
    @Produces(MediaType.APPLICATION_JSON)
    fun listAccountTypes(): List<EnumOptionDTO> {
        return investmentService.listInvestmentTypes().map {
            EnumOptionDTO(
                key = it.name,
                label = investmentTypeLabelsPTBR[it] ?: it.name,
            )
        }
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
