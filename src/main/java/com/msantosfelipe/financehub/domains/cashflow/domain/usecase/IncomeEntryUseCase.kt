package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeType
import com.msantosfelipe.financehub.domains.cashflow.ports.input.IncomeEntryServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.IncomeEntryRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class IncomeEntryUseCase(
    val repository: IncomeEntryRepositoryPort,
) : IncomeEntryServicePort {
    override suspend fun createIncomeEntry(incomeEntry: IncomeEntry): UUID = repository.createIncomeEntry(incomeEntry)

    override suspend fun getIncomeEntryById(id: UUID): IncomeEntry = repository.getIncomeEntryById(id)

    override suspend fun updateIncomeEntry(incomeEntry: IncomeEntry): IncomeEntry = repository.updateIncomeEntry(incomeEntry)

    override suspend fun deleteIncomeEntry(id: UUID) = repository.deleteIncomeEntry(id)

    override suspend fun listIncomeEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<IncomeEntry> = repository.listIncomeEntriesByDateRange(initDate, endDate)

    override fun listIncomeTypes(): List<IncomeType> = IncomeType.entries
}
