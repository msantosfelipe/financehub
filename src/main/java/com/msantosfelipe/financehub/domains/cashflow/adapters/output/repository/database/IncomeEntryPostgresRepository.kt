package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeAmount
import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeEntry
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDate
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface IncomeEntryPostgresRepository : CoroutineCrudRepository<IncomeEntry, UUID> {
    @Query(
        """
    SELECT 
        COALESCE(SUM(gross_amount), 0) AS gross_amount,
        COALESCE(SUM(net_amount), 0) AS net_amount
    FROM incomes_entry
    WHERE reference_date = :referenceDate
    """,
    )
    suspend fun sumAmountsByReferenceDate(referenceDate: LocalDate): IncomeAmount

    suspend fun findByReferenceDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<IncomeEntry>
}
