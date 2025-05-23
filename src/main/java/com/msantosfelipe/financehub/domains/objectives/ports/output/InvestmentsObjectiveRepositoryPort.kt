package com.msantosfelipe.financehub.domains.objectives.ports.output

import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjective
import java.util.UUID

interface InvestmentsObjectiveRepositoryPort {
    suspend fun create(objective: InvestmentsObjective): UUID

    suspend fun update(objective: InvestmentsObjective): InvestmentsObjective

    suspend fun getAll(): List<InvestmentsObjective>

    suspend fun getById(id: UUID): InvestmentsObjective
}
