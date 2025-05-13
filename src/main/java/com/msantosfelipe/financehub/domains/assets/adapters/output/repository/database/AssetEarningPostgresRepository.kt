package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarningReport
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarning
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDate
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface AssetEarningPostgresRepository : CoroutineCrudRepository<AssetEarning, UUID> {
    @Query("SELECT * FROM asset_earnings WHERE asset_id = :assetId AND reference_date = :referenceDate")
    suspend fun findByAssetIdAndReferenceDate(
        assetId: UUID,
        referenceDate: LocalDate,
    ): AssetEarning?

    suspend fun findByAssetId(assetId: UUID): List<AssetEarning>

    @Query(
        "SELECT " +
            "TO_CHAR(e.reference_date, 'YYYY-MM') AS reference_date, " +
            "a.type AS asset_type," +
            "a.ticker, " +
            "e.total_amount_received " +
            "FROM asset_earnings e " +
            "INNER JOIN assets a ON e.asset_id = a.id " +
            "WHERE e.reference_date BETWEEN :initDate AND :endDate " +
            "ORDER BY reference_date, a.type, a.ticker",
    )
    suspend fun listEarningsByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<AssetEarningReport>
}
