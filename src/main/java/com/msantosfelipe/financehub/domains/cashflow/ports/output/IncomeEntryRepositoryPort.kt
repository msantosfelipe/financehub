package com.msantosfelipe.financehub.domains.cashflow.ports.output

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import java.time.LocalDate
import java.util.UUID

interface IncomeEntryRepositoryPort {
    suspend fun createIncomeEntry(incomeEntry: IncomeEntry): UUID

    suspend fun updateIncomeEntry(incomeEntry: IncomeEntry): IncomeEntry

    suspend fun deleteIncomeEntry(id: UUID)

    suspend fun listIncomeEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<IncomeEntry>
}
