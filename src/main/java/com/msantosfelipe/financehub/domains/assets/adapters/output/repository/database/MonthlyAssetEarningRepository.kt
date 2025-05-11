package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import com.msantosfelipe.financehub.domains.assets.ports.output.MonthlyAssetEarningRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class MonthlyAssetEarningRepository(
    val repository: MonthlyAssetEarningPostgresRepository,
) : MonthlyAssetEarningRepositoryPort {
    override suspend fun createEarningEntry(assetEarning: MonthlyAssetEarning): UUID = repository.save(assetEarning).id

    override suspend fun updateEarningEntry(assetEarning: MonthlyAssetEarning): UUID = repository.update(assetEarning).id

    override suspend fun findByAssetIdAndReferenceDate(
        assetId: UUID,
        referenceDate: LocalDate,
    ): MonthlyAssetEarning? = repository.findByAssetIdAndReferenceDate(assetId, referenceDate)
}
