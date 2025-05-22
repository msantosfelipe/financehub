package com.msantosfelipe.financehub.domains.balance.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.balance.domain.model.MonthlyBalance
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDate
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface BalancePostgresRepository : CoroutineCrudRepository<MonthlyBalance, UUID> {
    suspend fun findByReferenceDate(date: LocalDate): MonthlyBalance?
}
