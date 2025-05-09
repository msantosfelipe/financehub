package com.msantosfelipe.financehub.objectives.adapters.input.rest.controllers

import com.msantosfelipe.financehub.objective.adapters.input.rest.dto.CreateInvestmentsObjectiveRequestDto
import com.msantosfelipe.financehub.objective.adapters.input.rest.dto.UpdateInvestmentsObjectiveRequestDto
import com.msantosfelipe.financehub.objective.domain.model.InvestmentsObjective
import com.msantosfelipe.financehub.objective.ports.input.InvestmentsObjectiveServicePort
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
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
}
