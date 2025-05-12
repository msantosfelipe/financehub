package com.msantosfelipe.financehub.domains.assets.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@MappedEntity(value = "asset_earnings")
@Serdeable
data class MonthlyAssetEarning(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    @MappedProperty("asset_id")
    val assetId: UUID,
    @Column(name = "total_amount_received", precision = 15, scale = 2)
    var totalAmountReceived: BigDecimal,
    val referenceDate: LocalDate,
    val notes: String?,
)

@Serdeable
data class AssetEarningReport(
    val referenceDate: String,
    val ticker: String,
    var totalAmountReceived: BigDecimal,
)

@Serdeable
data class GroupedEarningReport(
    val date: String,
    val earnings: List<AssetEarningItem>
)

@Serdeable
data class AssetEarningItem(
    val ticker: String,
    val amount: BigDecimal
)
