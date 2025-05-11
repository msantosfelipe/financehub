package com.msantosfelipe.financehub.domains.objectives.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate
import java.util.UUID

@MappedEntity(value = "investments_objective")
@Serdeable
data class InvestmentsObjective(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    @MappedProperty("objective_horizon")
    val horizon: InvestmentsObjectiveHorizon,
    val estimatedDate: LocalDate? = null,
    val createdDate: LocalDate,
    val updatedDate: LocalDate? = null,
    val description: String,
    val status: InvestmentsObjectiveStatus? = InvestmentsObjectiveStatus.ACTIVE,
)

enum class InvestmentsObjectiveHorizon {
    SHORT_TERM,
    MEDIUM_TERM,
    LONG_TERM,
}

enum class InvestmentsObjectiveStatus {
    ACTIVE,
    COMPLETED,
    CANCELED,
}
