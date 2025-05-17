package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseCategory
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.input.ExpenseEntryServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.ExpenseCategoryRepositoryPort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.ExpenseEntryRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class ExpenseEntryUseCase(
    val repository: ExpenseEntryRepositoryPort,
    val expenseCategoryRepository: ExpenseCategoryRepositoryPort,
) : ExpenseEntryServicePort {
    override suspend fun createExpenseEntry(expenseEntry: ExpenseEntry): UUID = repository.createExpenseEntry(expenseEntry)

    override suspend fun getExpenseCategory(categoryName: String): ExpenseCategory =
        expenseCategoryRepository.getExpenseCategoryByName(categoryName)
            ?: expenseCategoryRepository.createExpenseCategory(ExpenseCategory(name = categoryName))

    override suspend fun getAllExpenseCategories(): List<ExpenseCategory> = expenseCategoryRepository.listExpenseCategories()

    override suspend fun getExpenseEntryById(id: UUID): ExpenseEntry = repository.getExpenseEntryById(id)

    override suspend fun updateExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry = repository.updateExpenseEntry(expenseEntry)

    override suspend fun deleteExpenseEntry(id: UUID) = repository.deleteExpenseEntry(id)

    override suspend fun listExpenseEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry> = repository.listExpenseEntriesByDateRange(initDate, endDate)
}
