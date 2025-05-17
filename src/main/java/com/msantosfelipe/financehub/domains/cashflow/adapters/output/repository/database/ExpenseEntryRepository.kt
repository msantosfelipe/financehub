package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.output.ExpenseEntryRepositoryPort
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class ExpenseEntryRepository(
    val repository: ExpenseEntryMongoRepository,
) : ExpenseEntryRepositoryPort {
    val domainType = "expense"

    override suspend fun createExpenseEntry(expenseEntry: ExpenseEntry): UUID = repository.save(entity = expenseEntry).id

    override suspend fun getExpenseEntryById(id: UUID): ExpenseEntry =
        repository.findById(id) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "id",
            value = id.toString(),
        )

    override suspend fun listExpenseCategories(): List<String> = repository.listExpenseCategories()

    override suspend fun updateExpenseEntry(expenseEntry: ExpenseEntry): ExpenseEntry = repository.update(entity = expenseEntry)

    override suspend fun deleteExpenseEntry(id: UUID) {
        repository.deleteById(id)
    }

    override suspend fun listExpenseEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry> = repository.findByReferenceDateBetween(startDate = initDate, endDate = endDate)
}
