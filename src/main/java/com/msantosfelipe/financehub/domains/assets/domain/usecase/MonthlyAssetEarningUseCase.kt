package com.msantosfelipe.financehub.domains.assets.domain.usecase

import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarningItem
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarningReport
import com.msantosfelipe.financehub.domains.assets.domain.model.GroupedEarningReport
import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import com.msantosfelipe.financehub.domains.assets.ports.input.MonthlyAssetEarningServicePort
import com.msantosfelipe.financehub.domains.assets.ports.output.MonthlyAssetEarningRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
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
                    totalAmountReceived = assetEarning.totalAmountReceived,
                    notes = assetEarning.notes,
                ),
            )
        }
    }

    override suspend fun listEarningsByAsset(assetId: UUID): List<MonthlyAssetEarning> =
        monthlyAssetEarningRepository.listEarningsByAsset(assetId)

    override suspend fun listEarningsByDateRange(
        initDate: LocalDate,
        endDate: LocalDate
    ): List<GroupedEarningReport> {
        val reportList = monthlyAssetEarningRepository.listEarningsByDateRange(initDate, endDate)
        return groupByMonthAndTicker(reportList)
    }

    fun groupByMonthAndTicker(reportList: List<AssetEarningReport>): List<GroupedEarningReport> {
        return reportList
            .groupBy { it.referenceDate }
            .map { (date, earnings) ->
                GroupedEarningReport(
                    date = date,
                    earnings = earnings.map {
                        AssetEarningItem(
                            ticker = it.ticker,
                            amount = it.totalAmountReceived
                        )
                    }
                )
            }
            .sortedBy { it.date }
    }
}
