package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseAmount
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseCategory
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.output.ExpenseCategoryRepositoryPort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.ExpenseEntryRepositoryPort
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import java.time.LocalDate
import java.util.UUID

@Singleton
class ExpenseEntryRepository(
    val repository: ExpenseEntryPostgresRepository,
) : ExpenseEntryRepositoryPort {
    override suspend fun createExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry = repository.save(entity = expenseEntry)

    override suspend fun getExpenseEntryById(id: UUID): ExpenseEntry = repository.findById(id)

    override suspend fun sumAmountsByReferenceDate(referenceDate: LocalDate): ExpenseAmount {
        return repository.sumAmountsByReferenceDate(referenceDate)
    }

    override suspend fun updateExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry = repository.update(entity = expenseEntry)

    override suspend fun deleteExpenseEntry(id: UUID) {
        repository.deleteById(id)
    }

    override suspend fun listExpenseEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry> = repository.findByReferenceDateBetween(startDate = initDate, endDate = endDate)
}

@Singleton
class ExpenseCategoryRepository(
    val repository: ExpenseCategoryPostgresRepository,
) : ExpenseCategoryRepositoryPort {
    override suspend fun createExpenseCategory(category: ExpenseCategory): ExpenseCategory = repository.save(category)

    override suspend fun getExpenseCategoryByName(categoryName: String): ExpenseCategory? = repository.findByName(categoryName)

    override suspend fun listExpenseCategories(): List<ExpenseCategory> = repository.findAll().toList()
}
