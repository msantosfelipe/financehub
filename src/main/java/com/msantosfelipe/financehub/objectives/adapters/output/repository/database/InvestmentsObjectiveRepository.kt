package com.msantosfelipe.financehub.objectives.adapters.output.repository.database

import com.msantosfelipe.financehub.commons.ports.output.exceptions.GenericNotFoundException
import com.msantosfelipe.financehub.objectives.domain.model.InvestmentsObjective
import com.msantosfelipe.financehub.objectives.ports.output.InvestmentsObjectiveRepositoryPort
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import java.util.UUID

@Singleton
class InvestmentsObjectiveRepository(
    val repository: InvestmentsObjectivePostgresRepository,
) : InvestmentsObjectiveRepositoryPort {
    override suspend fun create(objective: InvestmentsObjective): UUID = repository.save(entity = objective).id

    override suspend fun update(objective: InvestmentsObjective): InvestmentsObjective = repository.update(entity = objective)

    override suspend fun getAll(): List<InvestmentsObjective> = repository.findAll().toList()

    override suspend fun getById(id: UUID): InvestmentsObjective =
        repository.findById(id) ?: throw GenericNotFoundException(
            domainType = "Investment Objective",
            id = id.toString(),
        )
}
