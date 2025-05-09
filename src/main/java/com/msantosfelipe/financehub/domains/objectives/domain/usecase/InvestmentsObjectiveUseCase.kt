package com.msantosfelipe.financehub.domains.objectives.domain.usecase

import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjective
import com.msantosfelipe.financehub.domains.objectives.ports.input.InvestmentsObjectiveServicePort
import com.msantosfelipe.financehub.domains.objectives.ports.output.InvestmentsObjectiveRepositoryPort
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class InvestmentsObjectiveUseCase(
    val objectiveRepository: InvestmentsObjectiveRepositoryPort,
) : InvestmentsObjectiveServicePort {
    override suspend fun createObjective(objective: InvestmentsObjective): UUID = objectiveRepository.create(objective)

    override suspend fun updateObjective(objective: InvestmentsObjective): InvestmentsObjective = objectiveRepository.update(objective)

    override suspend fun getAllObjectives(): List<InvestmentsObjective> = objectiveRepository.getAll()

    override suspend fun getObjectiveById(id: UUID): InvestmentsObjective = objectiveRepository.getById(id)
}
