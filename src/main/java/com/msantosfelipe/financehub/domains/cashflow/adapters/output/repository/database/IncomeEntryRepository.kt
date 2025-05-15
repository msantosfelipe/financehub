package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.output.IncomeEntryRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class IncomeEntryRepository(
    val repository: IncomeEntryMongoRepository,
) : IncomeEntryRepositoryPort {
    override suspend fun createIncomeEntry(incomeEntry: IncomeEntry): UUID = repository.save(entity = incomeEntry).id

    override suspend fun updateIncomeEntry(incomeEntry: IncomeEntry): IncomeEntry = repository.update(entity = incomeEntry)

    override suspend fun deleteIncomeEntry(id: UUID) {
        repository.deleteById(id)
    }

    override suspend fun listIncomeEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<IncomeEntry> = repository.findByReferenceDateBetween(startDate = initDate, endDate = endDate)
}
