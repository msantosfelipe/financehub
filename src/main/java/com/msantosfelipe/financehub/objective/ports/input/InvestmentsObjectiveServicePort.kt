package com.msantosfelipe.financehub.objective.ports.input

import com.msantosfelipe.financehub.objective.domain.model.InvestmentsObjective
import java.util.UUID

interface InvestmentsObjectiveServicePort {
    suspend fun createObjective(objective: InvestmentsObjective): UUID

    suspend fun updateObjective(objective: InvestmentsObjective): InvestmentsObjective

    suspend fun getAllObjectives(): List<InvestmentsObjective>

    suspend fun getObjectiveById(id: UUID): InvestmentsObjective
}
