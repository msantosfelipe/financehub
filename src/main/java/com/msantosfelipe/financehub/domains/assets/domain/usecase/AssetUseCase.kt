package com.msantosfelipe.financehub.domains.assets.domain.usecase

import com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage.AlphaVantageRepository
import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetType
import com.msantosfelipe.financehub.domains.assets.ports.input.AssetServicePort
import com.msantosfelipe.financehub.domains.assets.ports.output.AssetRepositoryPort
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class AssetUseCase(
    val assetRepository: AssetRepositoryPort,
    val alphaVantageRepository: AlphaVantageRepository,
) : AssetServicePort {
    override suspend fun createAsset(ticker: String): UUID {
        val alphaVantage = alphaVantageRepository.tickerSearch(ticker = ticker)

        return assetRepository.create(
            Asset(
                ticker = ticker,
                name = alphaVantage.name,
                type = convertAssetTypeFromAlphaVantage(alphaVantage.type),
                region = alphaVantage.region,
            ),
        )
    }

    override suspend fun getAllAssets(): List<Asset> = assetRepository.getAll()

    override suspend fun getAssetByTicker(ticker: String): Asset = assetRepository.getByTicker(ticker)

    override fun listAssetTypes(): List<AssetType> = AssetType.entries

    fun convertAssetTypeFromAlphaVantage(type: String): AssetType =
        when (type.lowercase()) {
            "equity" -> AssetType.STOCK
            "mutual fund" -> AssetType.REIT
            else -> AssetType.UNKNOWN
        }
}
