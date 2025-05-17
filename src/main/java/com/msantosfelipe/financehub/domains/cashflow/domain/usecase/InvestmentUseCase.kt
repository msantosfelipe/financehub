package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.Investment
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentType
import com.msantosfelipe.financehub.domains.cashflow.ports.input.InvestmentServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.InvestmentRepositoryPort
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class InvestmentUseCase(
    val repository: InvestmentRepositoryPort,
) : InvestmentServicePort {
    override suspend fun createInvestment(investment: Investment): UUID = repository.createInvestment(investment)

    override suspend fun getInvestmentById(id: UUID): Investment = repository.getInvestmentById(id)

    override suspend fun listActiveInvestments(): List<Investment> = repository.listActiveInvestments()

    override suspend fun updateInvestment(investment: Investment): Investment = repository.updateInvestment(investment)

    override fun listInvestmentTypes(): List<InvestmentType> = InvestmentType.entries
}
