package com.msantosfelipe.financehub.domains.assets.adapters.input.rest.dto

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal

@Serdeable
data class AssetEarningRequest(
    val ticker: String,
    val amountReceived: BigDecimal,
    val referenceDate: String,
    val notes: String?,
)
