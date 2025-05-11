package com.msantosfelipe.financehub.domains.assets.domain.usecase

import com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage.client.AlphaVantageClient
import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetType
import com.msantosfelipe.financehub.domains.assets.ports.input.AssetServicePort
import com.msantosfelipe.financehub.domains.assets.ports.output.AssetRepositoryPort
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class AssetUseCase(
    val assetRepository: AssetRepositoryPort,
    val alphaVantageClient: AlphaVantageClient,
    @Value("\${micronaut.http.services.alpha-vantage.apikey}") private val apiKey: String,
) : AssetServicePort {
    override suspend fun createAsset(ticker: String): UUID {
        val alphaVantage = alphaVantageClient.tickerSearch(ticker = ticker, apiKey = apiKey)

        return assetRepository.create(
            Asset(
                ticker = ticker,
                name = alphaVantage.bestMatches.firstOrNull()?.name ?: "Unknown",
                type = AssetType.STOCK,
                country = "TODO",
            ),
        )
    }

    override suspend fun getAllAssets(): List<Asset> = assetRepository.getAll()

    override suspend fun getAssetByTicker(ticker: String): Asset = assetRepository.getByTicker(ticker)
}
