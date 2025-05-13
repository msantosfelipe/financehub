package com.msantosfelipe.financehub.domains.assets.ports.input

import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarning
import com.msantosfelipe.financehub.domains.assets.domain.model.EarningGroupByMonth
import java.time.LocalDate
import java.util.UUID

interface AssetEarningServicePort {
    suspend fun createOrUpdateEarningEntry(assetEarning: AssetEarning): UUID

    suspend fun listEarningsByAsset(assetId: UUID): List<AssetEarning>

    suspend fun listEarningsByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<EarningGroupByMonth>
}
