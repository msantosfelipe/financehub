package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseCategory
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import io.micronaut.data.annotation.Join
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDate
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ExpenseCategoryPostgresRepository : CoroutineCrudRepository<ExpenseCategory, UUID> {
    suspend fun findByName(name: String): ExpenseCategory?
}

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ExpenseEntryPostgresRepository : CoroutineCrudRepository<ExpenseEntry, UUID> {
    @Join("category")
    override suspend fun findById(uuid: UUID): ExpenseEntry

    @Join("category")
    suspend fun findByReferenceDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<ExpenseEntry>
}
