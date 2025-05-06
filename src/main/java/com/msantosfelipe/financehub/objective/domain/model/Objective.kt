package com.msantosfelipe.financehub.objective.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.util.Date
import java.util.UUID

@MappedEntity(value = "objectives")
@Serdeable
data class Objective(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val horizon: ObjectiveHorizon,
    val estimatedDate: Date?,
    val createdDate: Date,
    val updatedDate: Date,
    val description: String,
    val status: ObjectiveStatus,
)

enum class ObjectiveHorizon {
    SHORT_TERM,
    MEDIUM_TERM,
    LONG_TERM,
}

enum class ObjectiveStatus {
    ACTIVE,
    COMPLETED,
    CANCELED,
}
