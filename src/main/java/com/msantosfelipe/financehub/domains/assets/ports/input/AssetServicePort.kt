package com.msantosfelipe.financehub.domains.assets.ports.input

import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import java.util.UUID

interface AssetServicePort {
    suspend fun createAsset(ticker: String): UUID

    suspend fun getAllAssets(): List<Asset>

    suspend fun getAssetByTicker(ticker: String): Asset
}
