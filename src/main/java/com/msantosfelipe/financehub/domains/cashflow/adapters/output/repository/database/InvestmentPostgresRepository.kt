package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.Investment
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentEntry
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDate
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface InvestmentPostgresRepository : CoroutineCrudRepository<Investment, UUID> {
    suspend fun findByActiveTrue(): List<Investment>
}

@JdbcRepository(dialect = Dialect.POSTGRES)
interface InvestmentEntryPostgresRepository : CoroutineCrudRepository<InvestmentEntry, UUID> {
    suspend fun findByReferenceDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<InvestmentEntry>
}
