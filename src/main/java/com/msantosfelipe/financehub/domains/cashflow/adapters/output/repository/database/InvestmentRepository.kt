package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.Investment
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.output.InvestmentEntryRepositoryPort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.InvestmentRepositoryPort
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import jakarta.inject.Singleton
import java.time.LocalDate
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

@Singleton
class InvestmentEntryRepository(
    val repository: InvestmentEntryPostgresRepository,
) : InvestmentEntryRepositoryPort {
    val domainType = "Investment"

    override suspend fun createInvestmentEntry(investmentEntry: InvestmentEntry): UUID = repository.save(entity = investmentEntry).id

    override suspend fun getInvestmentEntryById(id: UUID): InvestmentEntry =
        repository.findById(id) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "id",
            value = id.toString(),
        )

    override suspend fun updateInvestmentEntry(investmentEntry: InvestmentEntry): InvestmentEntry =
        repository.update(
            entity = investmentEntry,
        )

    override suspend fun deleteInvestmentEntry(id: UUID) {
        repository.deleteById(id)
    }

    override suspend fun listInvestmentEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<InvestmentEntry> = repository.findByReferenceDateBetween(startDate = initDate, endDate = endDate)
}
