package com.msantosfelipe.financehub.domains.cashflow.adapters.output.internal.balance

import com.msantosfelipe.financehub.domains.balance.adapters.input.internal.BalanceInternal
import com.msantosfelipe.financehub.domains.balance.adapters.input.internal.dto.PersistBalanceByExpenseDto
import jakarta.inject.Singleton
import java.math.BigDecimal
import java.time.LocalDate

@Singleton
class BalanceInternalClient(
    val balanceClient: BalanceInternal,
) {
    suspend fun persistBalanceByExpense(
        referenceDate: LocalDate,
        fixedExpenseAmount: BigDecimal,
        variableExpenseAmount: BigDecimal,
    ) = balanceClient.persistBalanceByExpense(
        expenseData =
            PersistBalanceByExpenseDto(
                referenceDate,
                fixedExpenseAmount,
                variableExpenseAmount,
            ),
    )
}
