package com.msantosfelipe.financehub.domains.assets.ports.input

import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import java.util.UUID

interface MonthlyAssetEarningServicePort {
    suspend fun createOrUpdateEarningEntry(assetEarning: MonthlyAssetEarning): UUID

    suspend fun listEarningsByAsset(assetId: UUID): List<MonthlyAssetEarning>
}
