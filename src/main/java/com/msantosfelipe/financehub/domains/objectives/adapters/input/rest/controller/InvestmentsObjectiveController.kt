package com.msantosfelipe.financehub.domains.objectives.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.objectives.adapters.input.rest.dto.CreateInvestmentsObjectiveRequestDto
import com.msantosfelipe.financehub.domains.objectives.adapters.input.rest.dto.UpdateInvestmentsObjectiveRequestDto
import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjective
import com.msantosfelipe.financehub.domains.objectives.domain.model.investmentsObjectiveStatusLabels
import com.msantosfelipe.financehub.domains.objectives.domain.model.nvestmentsObjectiveHorizonLabels
import com.msantosfelipe.financehub.domains.objectives.ports.input.InvestmentsObjectiveServicePort
import com.msantosfelipe.financehub.shared.adapters.rest.dto.EnumOptionDTO
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
import java.time.LocalDate
import java.util.UUID

@Controller("api/v1/objectives")
class InvestmentsObjectiveController(
    val objectiveService: InvestmentsObjectiveServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createAccount(
        @Body createInvestmentObjectiveRequest: CreateInvestmentsObjectiveRequestDto,
    ): UUID =
        objectiveService.createObjective(
            InvestmentsObjective(
                horizon = createInvestmentObjectiveRequest.horizon,
                estimatedDate = createInvestmentObjectiveRequest.estimatedDate,
                createdDate = LocalDate.now(),
                description = createInvestmentObjectiveRequest.description,
            ),
        )

    @Put(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateAccount(
        @PathVariable uuid: UUID,
        @Body updateInvestmentObjectiveRequest: UpdateInvestmentsObjectiveRequestDto,
    ): InvestmentsObjective {
        val objective = objectiveService.getObjectiveById(uuid)
        return objectiveService.updateObjective(
            objective =
                objective.copy(
                    horizon = updateInvestmentObjectiveRequest.horizon,
                    estimatedDate = updateInvestmentObjectiveRequest.estimatedDate,
                    updatedDate = LocalDate.now(),
                    description = updateInvestmentObjectiveRequest.description,
                    status = updateInvestmentObjectiveRequest.status,
                ),
        )
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAllObjectives(): List<InvestmentsObjective> = objectiveService.getAllObjectives()

    @Get("horizon")
    @Produces(MediaType.APPLICATION_JSON)
    fun listInvestmentsObjectiveHorizon(): List<EnumOptionDTO> {
        return objectiveService.listInvestmentsObjectiveHorizon().map {
            EnumOptionDTO(
                key = it.name,
                label = nvestmentsObjectiveHorizonLabels[it] ?: it.name,
            )
        }
    }

    @Get("status")
    @Produces(MediaType.APPLICATION_JSON)
    fun listInvestmentsObjectiveStatus(): List<EnumOptionDTO> {
        return objectiveService.listInvestmentsObjectiveStatus().map {
            EnumOptionDTO(
                key = it.name,
                label = investmentsObjectiveStatusLabels[it] ?: it.name,
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
