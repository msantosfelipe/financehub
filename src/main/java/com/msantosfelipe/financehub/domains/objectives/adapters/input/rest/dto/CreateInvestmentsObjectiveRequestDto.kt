package com.msantosfelipe.financehub.domains.objectives.adapters.input.rest.dto

import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjectiveHorizon
import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjectiveStatus
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
