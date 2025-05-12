package com.msantosfelipe.financehub.domains.assets.ports.input

import com.msantosfelipe.financehub.domains.assets.domain.model.GroupedEarningReport
import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import java.time.LocalDate
import java.util.UUID

interface MonthlyAssetEarningServicePort {
    suspend fun createOrUpdateEarningEntry(assetEarning: MonthlyAssetEarning): UUID

    suspend fun listEarningsByAsset(assetId: UUID): List<MonthlyAssetEarning>
    suspend fun listEarningsByDateRange(initDate: LocalDate, endDate: LocalDate): List<GroupedEarningReport>
}
