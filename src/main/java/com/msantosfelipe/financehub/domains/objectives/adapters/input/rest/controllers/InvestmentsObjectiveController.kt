package com.msantosfelipe.financehub.domains.objectives.adapters.input.rest.controllers

import com.msantosfelipe.financehub.domains.objectives.adapters.input.rest.dto.CreateInvestmentsObjectiveRequestDto
import com.msantosfelipe.financehub.domains.objectives.adapters.input.rest.dto.UpdateInvestmentsObjectiveRequestDto
import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjective
import com.msantosfelipe.financehub.domains.objectives.ports.input.InvestmentsObjectiveServicePort
import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
import com.msantosfelipe.financehub.shared.exceptions.rest.httpConversionErrorHandler
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

    @Error(global = true)
    fun handleConversionError(
        request: HttpRequest<*>,
        ex: ConversionErrorException,
    ): HttpResponse<ErrorDto> {
        return HttpResponse.badRequest(
            httpConversionErrorHandler(ex),
        )
    }
}
