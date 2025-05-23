package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseCategory
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.input.BalanceServicePort
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
    val balanceService: BalanceServicePort,
) : ExpenseEntryServicePort {
    override suspend fun createExpenseEntry(expenseEntry: ExpenseEntry): UUID {
        val createdEntry = repository.createExpenseEntry(expenseEntry)
        persistBalance(createdEntry.referenceDate)
        return createdEntry.id
    }

    override suspend fun getExpenseCategory(categoryName: String): ExpenseCategory =
        expenseCategoryRepository.getExpenseCategoryByName(categoryName)
            ?: expenseCategoryRepository.createExpenseCategory(ExpenseCategory(name = categoryName))

    override suspend fun getAllExpenseCategories(): List<ExpenseCategory> = expenseCategoryRepository.listExpenseCategories()

    override suspend fun getExpenseEntryById(id: UUID): ExpenseEntry = repository.getExpenseEntryById(id)

    override suspend fun updateExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry {
        val updatedEntry = repository.updateExpenseEntry(expenseEntry)
        persistBalance(updatedEntry.referenceDate)
        return updatedEntry
    }

    override suspend fun deleteExpenseEntry(id: UUID) {
        val expense = getExpenseEntryById(id)
        repository.deleteExpenseEntry(id)
        persistBalance(expense.referenceDate)
    }

    override suspend fun listExpenseEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry> = repository.listExpenseEntriesByDateRange(initDate, endDate)

    suspend fun persistBalance(referenceDate: LocalDate) {
        val expenseAmount = repository.sumAmountsByReferenceDate(referenceDate)

        balanceService.persistBalanceByExpense(
            referenceDate = referenceDate,
            fixedExpenseAmount = expenseAmount.fixedExpenseAmount,
            variableExpenseAmount = expenseAmount.variableExpenseAmount,
        )
    }
}
