package com.msantosfelipe.financehub.domains.assets.domain.usecase

import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import com.msantosfelipe.financehub.domains.assets.ports.input.MonthlyAssetEarningServicePort
import com.msantosfelipe.financehub.domains.assets.ports.output.MonthlyAssetEarningRepositoryPort
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class MonthlyAssetEarningUseCase(
    val monthlyAssetEarningRepository: MonthlyAssetEarningRepositoryPort,
) : MonthlyAssetEarningServicePort {
    override suspend fun createOrUpdateEarningEntry(assetEarning: MonthlyAssetEarning): UUID {
        val existingEarning =
            monthlyAssetEarningRepository.findByAssetIdAndReferenceDate(
                assetId = assetEarning.assetId,
                referenceDate = assetEarning.referenceDate,
            )
        return if (existingEarning == null) {
            monthlyAssetEarningRepository.createEarningEntry(assetEarning)
        } else {
            monthlyAssetEarningRepository.updateEarningEntry(
                existingEarning.copy(
                    amountReceived = assetEarning.amountReceived,
                    notes = assetEarning.notes,
                ),
            )
        }
    }
}
