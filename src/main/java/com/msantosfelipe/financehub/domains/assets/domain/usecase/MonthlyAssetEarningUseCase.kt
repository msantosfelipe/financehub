package com.msantosfelipe.financehub.domains.assets.domain.usecase

import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarningReport
import com.msantosfelipe.financehub.domains.assets.domain.model.EarningGroupByMonth
import com.msantosfelipe.financehub.domains.assets.domain.model.EarningItem
import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import com.msantosfelipe.financehub.domains.assets.ports.input.MonthlyAssetEarningServicePort
import com.msantosfelipe.financehub.domains.assets.ports.output.MonthlyAssetEarningRepositoryPort
import jakarta.inject.Singleton
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale
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
        endDate: LocalDate,
    ): List<EarningGroupByMonth> {
        val reportList = monthlyAssetEarningRepository.listEarningsByDateRange(initDate, endDate)
        return groupByMonthAndTicker(reportList)
    }

    fun groupByMonthAndTicker(reports: List<AssetEarningReport>): List<EarningGroupByMonth> {
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return reports
            .groupBy { it.referenceDate }
            .map { (date, earningsByDate) ->
                val earningsGroupedByType =
                    earningsByDate
                        .groupBy { it.assetType }
                        .mapValues { (_, earningsByType) ->
                            earningsByType.map {
                                EarningItem(
                                    ticker = it.ticker,
                                    amount = formatter.format(it.totalAmountReceived),
                                )
                            }
                        }

                EarningGroupByMonth(
                    date = date,
                    earnings = earningsGroupedByType,
                )
            }
    }
}
