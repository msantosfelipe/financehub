package com.msantosfelipe.financehub.domains.balance.adapters.input.internal.dto

import java.math.BigDecimal
import java.time.LocalDate

data class PersistBalanceDto(
    val referenceDate: LocalDate,
    val totalGrossIncomes: BigDecimal,
    val totalNetIncomes: BigDecimal,
    val totalFixedExpenses: BigDecimal,
    val totalOtherExpenses: BigDecimal,
    val totalInvested: BigDecimal,
)
