package com.msantosfelipe.financehub.objective.adapters.input.rest.dto

import com.msantosfelipe.financehub.objective.domain.model.InvestmentsObjectiveHorizon
import com.msantosfelipe.financehub.objective.domain.model.InvestmentsObjectiveStatus
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate

@Serdeable
data class CreateInvestmentsObjectiveRequestDto(
    val horizon: InvestmentsObjectiveHorizon,
    val estimatedDate: LocalDate?,
    val description: String,
)

@Serdeable
data class UpdateInvestmentsObjectiveRequestDto(
    val horizon: InvestmentsObjectiveHorizon,
    val estimatedDate: LocalDate?,
    val description: String,
    val status: InvestmentsObjectiveStatus,
)
