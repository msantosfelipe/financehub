package com.msantosfelipe.financehub.domains.objectives.ports.input

import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjective
import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjectiveHorizon
import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjectiveStatus
import java.util.UUID

interface InvestmentsObjectiveServicePort {
    suspend fun createObjective(objective: InvestmentsObjective): UUID

    suspend fun updateObjective(objective: InvestmentsObjective): InvestmentsObjective

    suspend fun getAllObjectives(): List<InvestmentsObjective>

    suspend fun getObjectiveById(id: UUID): InvestmentsObjective

    fun listInvestmentsObjectiveHorizon(): List<InvestmentsObjectiveHorizon> = InvestmentsObjectiveHorizon.entries

    fun listInvestmentsObjectiveStatus(): List<InvestmentsObjectiveStatus> = InvestmentsObjectiveStatus.entries
}
