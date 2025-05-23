package com.msantosfelipe.financehub.domains.cashflow.ports.output

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseAmount
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseCategory
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import java.time.LocalDate
import java.util.UUID

interface ExpenseEntryRepositoryPort {
    suspend fun createExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry

    suspend fun getExpenseEntryById(id: UUID): ExpenseEntry

    suspend fun sumAmountsByReferenceDate(referenceDate: LocalDate): ExpenseAmount

    suspend fun updateExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry

    suspend fun deleteExpenseEntry(id: UUID)

    suspend fun listExpenseEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry>
}

interface ExpenseCategoryRepositoryPort {
    suspend fun createExpenseCategory(category: ExpenseCategory): ExpenseCategory

    suspend fun getExpenseCategoryByName(categoryName: String): ExpenseCategory?

    suspend fun listExpenseCategories(): List<ExpenseCategory>
}
