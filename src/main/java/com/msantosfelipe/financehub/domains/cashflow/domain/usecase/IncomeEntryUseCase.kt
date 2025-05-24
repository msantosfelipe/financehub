package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeType
import com.msantosfelipe.financehub.domains.cashflow.ports.input.BalanceServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.input.IncomeEntryServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.IncomeEntryRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class IncomeEntryUseCase(
    val repository: IncomeEntryRepositoryPort,
    val balanceService: BalanceServicePort,
) : IncomeEntryServicePort {
    override suspend fun createIncomeEntry(incomeEntry: IncomeEntry): UUID {
        val createdEntry = repository.createIncomeEntry(incomeEntry)
        persistBalance(createdEntry.referenceDate)
        return createdEntry.id
    }

    override suspend fun getIncomeEntryById(id: UUID): IncomeEntry = repository.getIncomeEntryById(id)

    override suspend fun updateIncomeEntry(incomeEntry: IncomeEntry): IncomeEntry {
        val updatedEntry = repository.updateIncomeEntry(incomeEntry)
        persistBalance(updatedEntry.referenceDate)
        return updatedEntry
    }

    override suspend fun deleteIncomeEntry(id: UUID) {
        val referenceDate = getIncomeEntryById(id).referenceDate
        repository.deleteIncomeEntry(id)
        persistBalance(referenceDate)
    }

    override suspend fun listIncomeEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<IncomeEntry> = repository.listIncomeEntriesByDateRange(initDate, endDate)

    override fun listIncomeTypes(): List<IncomeType> = IncomeType.entries

    suspend fun persistBalance(referenceDate: LocalDate) {
        val incomeAmount = repository.sumAmountsByReferenceDate(referenceDate)
        balanceService.persistBalanceByIncome(
            referenceDate = referenceDate,
            grossIncomeAmount = incomeAmount.grossAmount,
            netIncomeAmount = incomeAmount.netAmount,
        )
    }
}
