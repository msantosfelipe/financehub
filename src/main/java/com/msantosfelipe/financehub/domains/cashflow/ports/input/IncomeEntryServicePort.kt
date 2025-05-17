package com.msantosfelipe.financehub.domains.cashflow.ports.input

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeType
import java.time.LocalDate
import java.util.UUID

interface IncomeEntryServicePort {
    suspend fun createIncomeEntry(incomeEntry: IncomeEntry): UUID

    suspend fun getIncomeEntryById(id: UUID): IncomeEntry

    suspend fun updateIncomeEntry(incomeEntry: IncomeEntry): IncomeEntry

    suspend fun deleteIncomeEntry(id: UUID)

    suspend fun listIncomeEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<IncomeEntry>

    fun listIncomeTypes(): List<IncomeType>
}
