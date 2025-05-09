package com.msantosfelipe.financehub.domains.assets.domain.usecase

import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetType
import com.msantosfelipe.financehub.domains.assets.ports.input.AssetServicePort
import com.msantosfelipe.financehub.domains.assets.ports.output.AssetRepositoryPort
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class AssetUseCase(
    val assetRepository: AssetRepositoryPort,
) : AssetServicePort {
    override suspend fun createAsset(ticker: String): UUID {
        // TODO Implement Alpha Vantage API to get asset data
        return assetRepository.create(
            Asset(
                ticker = ticker,
                name = "TODO",
                type = AssetType.STOCK,
                country = "TODO",
            ),
        )
    }

    override suspend fun getAllAssets(): List<Asset> = assetRepository.getAll()

    override suspend fun getAssetByTicker(ticker: String): Asset = assetRepository.getByTicker(ticker)
}
