package com.msantosfelipe.financehub.domains.balance.ports.input

import java.math.BigDecimal
import java.time.LocalDate

interface BalanceServicePort {
    suspend fun persistBalanceByExpense(
        referenceDate: LocalDate,
        fixedExpenseAmount: BigDecimal,
        variableExpenseAmount: BigDecimal,
    )
}
