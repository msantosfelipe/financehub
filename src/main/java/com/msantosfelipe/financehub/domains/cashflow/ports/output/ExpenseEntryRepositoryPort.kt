package com.msantosfelipe.financehub.domains.cashflow.ports.output

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import java.time.LocalDate
import java.util.UUID

interface ExpenseEntryRepositoryPort {
    suspend fun createExpenseEntry(expenseEntry: ExpenseEntry): UUID

    suspend fun getExpenseEntryById(id: UUID): ExpenseEntry

    suspend fun listExpenseCategories(): List<String>

    suspend fun updateExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry

    suspend fun deleteExpenseEntry(id: UUID)

    suspend fun listExpenseEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry>
}
