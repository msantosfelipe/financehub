package com.msantosfelipe.financehub.domains.cashflow.ports.input

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseCategory
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import java.time.LocalDate
import java.util.UUID

interface ExpenseEntryServicePort {
    suspend fun createExpenseEntry(expenseEntry: ExpenseEntry): UUID

    suspend fun getExpenseCategory(categoryName: String): ExpenseCategory

    suspend fun getAllExpenseCategories(): List<ExpenseCategory>

    suspend fun getExpenseEntryById(id: UUID): ExpenseEntry

    suspend fun updateExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry

    suspend fun deleteExpenseEntry(id: UUID)

    suspend fun listExpenseEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry>
}
