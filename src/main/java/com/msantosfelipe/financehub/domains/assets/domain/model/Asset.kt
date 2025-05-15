package com.msantosfelipe.financehub.domains.assets.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@MappedEntity(value = "assets")
@Serdeable
data class Asset(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val ticker: String,
    val name: String,
    val type: AssetType,
    val region: String,
)

enum class AssetType {
    STOCK,
    REIT,
    UNKNOWN,
}

internal val assetTypeLabels =
    mapOf(
        AssetType.STOCK to "Ação",
        AssetType.REIT to "FII",
        AssetType.UNKNOWN to "Não Definido",
    )
