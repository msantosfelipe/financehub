package com.msantosfelipe.financehub.domains.accounts.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.accounts.domain.model.Account
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface AccountPostgresRepository : CoroutineCrudRepository<Account, UUID> {
    suspend fun findByName(name: String): Account?
}
