package com.msantosfelipe.financehub.domains.objectives.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjective
import com.msantosfelipe.financehub.domains.objectives.ports.output.InvestmentsObjectiveRepositoryPort
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import java.util.UUID

@Singleton
class InvestmentsObjectiveRepository(
    val repository: InvestmentsObjectivePostgresRepository,
) : InvestmentsObjectiveRepositoryPort {
    val domainType = "Investment Objective"

    override suspend fun create(objective: InvestmentsObjective): UUID = repository.save(entity = objective).id

    override suspend fun update(objective: InvestmentsObjective): InvestmentsObjective = repository.update(entity = objective)

    override suspend fun getAll(): List<InvestmentsObjective> = repository.findAll().toList()

    override suspend fun getById(id: UUID): InvestmentsObjective =
        repository.findById(id) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "id",
            value = id.toString(),
        )
}
