package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal

@Serdeable
data class CreateExpenseEntryDTO(
    val referenceDate: String,
    val category: String,
    val amount: BigDecimal,
    val description: String?,
    val isFixedExpense: Boolean,
)

@Serdeable
data class UpdateExpenseEntryDTO(
    val category: String,
    val amount: BigDecimal,
    val description: String?,
    val isFixedExpense: Boolean,
)
