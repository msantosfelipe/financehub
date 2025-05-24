package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.MonthlyBalance
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDate
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface BalancePostgresRepository : CoroutineCrudRepository<MonthlyBalance, UUID> {
    suspend fun findByReferenceDate(date: LocalDate): MonthlyBalance?

    @Query(
        """
    SELECT 
        TO_CHAR(reference_date, 'YYYY-MM') AS reference_date, 
        total_invested
    FROM balances
    WHERE EXTRACT(YEAR FROM reference_date) = EXTRACT(YEAR FROM CURRENT_DATE);
    """,
    )
    suspend fun listInvestedFromCurrentYear(): List<InvestmentReportRaw>

    @Query(
        """
    SELECT 
        TO_CHAR(reference_date, 'YYYY') AS reference_date,
        SUM(total_invested) AS total_invested
    FROM balances
    WHERE reference_date < DATE_TRUNC('year', CURRENT_DATE)
    GROUP BY TO_CHAR(reference_date, 'YYYY')
    ORDER BY reference_date DESC;;
    """,
    )
    suspend fun listTotalInvestedFromPastYears(): List<InvestmentReportRaw>
}
