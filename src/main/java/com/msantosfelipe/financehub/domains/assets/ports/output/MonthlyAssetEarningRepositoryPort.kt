package com.msantosfelipe.financehub.domains.assets.ports.output

import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import java.time.LocalDate
import java.util.UUID

interface MonthlyAssetEarningRepositoryPort {
    suspend fun createEarningEntry(assetEarning: MonthlyAssetEarning): UUID

    suspend fun updateEarningEntry(assetEarning: MonthlyAssetEarning): UUID

    suspend fun findByAssetIdAndReferenceDate(
        assetId: UUID,
        referenceDate: LocalDate,
    ): MonthlyAssetEarning?

    suspend fun listEarningsByAsset(assetId: UUID): List<MonthlyAssetEarning>
}
