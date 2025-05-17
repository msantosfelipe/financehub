package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDate
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ExpenseEntryMongoRepository : CoroutineCrudRepository<ExpenseEntry, UUID> {
    suspend fun findByReferenceDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry>

    @Query("SELECT DISTINCT category FROM expenses_entry ORDER BY category")
    suspend fun listExpenseCategories(): List<String>
}
