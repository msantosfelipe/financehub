package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeAmount
import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.output.IncomeEntryRepositoryPort
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class IncomeEntryRepository(
    val repository: IncomeEntryPostgresRepository,
) : IncomeEntryRepositoryPort {
    val domainType = "Income"

    override suspend fun createIncomeEntry(incomeEntry: IncomeEntry): IncomeEntry = repository.save(entity = incomeEntry)

    override suspend fun getIncomeEntryById(id: UUID): IncomeEntry =
        repository.findById(id) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "id",
            value = id.toString(),
        )

    override suspend fun sumAmountsByReferenceDate(referenceDate: LocalDate): IncomeAmount =
        repository.sumAmountsByReferenceDate(
            referenceDate,
        )

    override suspend fun updateIncomeEntry(incomeEntry: IncomeEntry): IncomeEntry = repository.update(entity = incomeEntry)

    override suspend fun deleteIncomeEntry(id: UUID) {
        repository.deleteById(id)
    }

    override suspend fun listIncomeEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<IncomeEntry> = repository.findByReferenceDateBetween(startDate = initDate, endDate = endDate)
}
