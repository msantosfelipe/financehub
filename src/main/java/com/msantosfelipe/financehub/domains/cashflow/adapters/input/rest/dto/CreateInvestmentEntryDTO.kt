package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.util.UUID

@Serdeable
data class CreateInvestmentEntryDTO(
    val referenceDate: String,
    val investmentId: UUID,
    val amount: BigDecimal,
)

data class UpdateInvestmentEntryDTO(
    val investmentId: UUID,
    val amount: BigDecimal,
)
