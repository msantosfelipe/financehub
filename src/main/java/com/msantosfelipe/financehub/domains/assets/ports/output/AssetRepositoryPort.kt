package com.msantosfelipe.financehub.domains.assets.ports.output

import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import java.util.UUID

interface AssetRepositoryPort {
    suspend fun create(asset: Asset): UUID

    suspend fun getAll(): List<Asset>

    suspend fun getByTicker(ticker: String): Asset
}
