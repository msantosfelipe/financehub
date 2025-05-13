package com.msantosfelipe.financehub.domains.assets.ports.output

import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarning
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarningReport
import java.time.LocalDate
import java.util.UUID

interface AssetEarningRepositoryPort {
    suspend fun createEarningEntry(assetEarning: AssetEarning): UUID

    suspend fun updateEarningEntry(assetEarning: AssetEarning): UUID

    suspend fun findByAssetIdAndReferenceDate(
        assetId: UUID,
        referenceDate: LocalDate,
    ): AssetEarning?

    suspend fun listEarningsByAsset(assetId: UUID): List<AssetEarning>

    suspend fun listEarningsByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<AssetEarningReport>
}
