package com.msantosfelipe.financehub.domains.balance.adapters.input.internal.dto

import java.math.BigDecimal
import java.time.LocalDate

data class PersistBalanceByExpenseDto(
    val referenceDate: LocalDate,
    val fixedExpenseAmount: BigDecimal,
    val variableExpenseAmount: BigDecimal,
)
