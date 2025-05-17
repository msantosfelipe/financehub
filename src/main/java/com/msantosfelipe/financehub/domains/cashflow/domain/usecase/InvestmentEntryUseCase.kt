package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.input.InvestmentEntryServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.InvestmentEntryRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class InvestmentEntryUseCase(
    val repository: InvestmentEntryRepositoryPort,
) : InvestmentEntryServicePort {
    override suspend fun createInvestmentEntry(investmentEntry: InvestmentEntry): UUID = repository.createInvestmentEntry(investmentEntry)

    override suspend fun getInvestmentEntryById(id: UUID): InvestmentEntry = repository.getInvestmentEntryById(id)

    override suspend fun updateInvestmentEntry(investmentEntry: InvestmentEntry): InvestmentEntry =
        repository.updateInvestmentEntry(
            investmentEntry,
        )

    override suspend fun deleteInvestmentEntry(id: UUID) = repository.deleteInvestmentEntry(id)

    override suspend fun listInvestmentEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<InvestmentEntry> = repository.listInvestmentEntriesByDateRange(initDate, endDate)
}
