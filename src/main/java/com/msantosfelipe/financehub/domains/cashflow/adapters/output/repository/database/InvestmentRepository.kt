package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.Investment
import com.msantosfelipe.financehub.domains.cashflow.ports.output.InvestmentRepositoryPort
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class InvestmentRepository(
    val repository: InvestmentPostgresRepository,
) : InvestmentRepositoryPort {
    val domainType = "Investment"

    override suspend fun createInvestment(investment: Investment): UUID = repository.save(investment).id

    override suspend fun getInvestmentById(id: UUID): Investment =
        repository.findById(id) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "id",
            value = id.toString(),
        )

    override suspend fun listActiveInvestments(): List<Investment> = repository.findByActiveTrue()

    override suspend fun updateInvestment(investment: Investment): Investment = repository.update(entity = investment)
}
