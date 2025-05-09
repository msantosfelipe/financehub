package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface AssetPostgresRepository : CoroutineCrudRepository<Asset, UUID> {
    suspend fun findByTicker(ticker: String): Asset?
}
