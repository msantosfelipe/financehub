package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarningReport
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarning
import com.msantosfelipe.financehub.domains.assets.ports.output.AssetEarningRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class AssetEarningRepository(
    val repository: AssetEarningPostgresRepository,
) : AssetEarningRepositoryPort {
    override suspend fun createEarningEntry(assetEarning: AssetEarning): UUID = repository.save(assetEarning).id

    override suspend fun updateEarningEntry(assetEarning: AssetEarning): UUID = repository.update(assetEarning).id

    override suspend fun findByAssetIdAndReferenceDate(
        assetId: UUID,
        referenceDate: LocalDate,
    ): AssetEarning? = repository.findByAssetIdAndReferenceDate(assetId, referenceDate)

    override suspend fun listEarningsByAsset(assetId: UUID): List<AssetEarning> = repository.findByAssetId(assetId)

    override suspend fun listEarningsByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<AssetEarningReport> = repository.listEarningsByDateRange(initDate, endDate)
}
