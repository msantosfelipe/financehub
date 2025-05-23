package com.msantosfelipe.financehub.domains.cashflow.ports.input

import java.math.BigDecimal
import java.time.LocalDate

interface BalanceServicePort {
    suspend fun persistBalanceByExpense(
        referenceDate: LocalDate,
        fixedExpenseAmount: BigDecimal,
        variableExpenseAmount: BigDecimal,
    )

    suspend fun persistBalanceByInvestment(
        referenceDate: LocalDate,
        investmentAmount: BigDecimal,
    )
}
