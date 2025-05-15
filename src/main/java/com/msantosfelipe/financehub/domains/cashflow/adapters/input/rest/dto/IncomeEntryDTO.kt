package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto

import com.msantosfelipe.financehub.domains.cashflow.domain.model.IncomeType
import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal

@Serdeable
data class IncomeEntryDTO(
    val referenceDate: String,
    val grossAmount: BigDecimal,
    val discountAmount: BigDecimal,
    val netAmount: BigDecimal,
    val type: IncomeType,
    val description: String?,
)
