package com.msantosfelipe.financehub.domains.assets.adapters.input.rest.dto

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDate

@Serdeable
data class AssetEarningRequest(
    val ticker: String,
    val amountReceived: BigDecimal,
    val referenceDate: LocalDate,
    val notes: String?,
)
